import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java") // Java支持
    alias(libs.plugins.kotlin) // Kotlin支持
    alias(libs.plugins.intelliJPlatform) // IntelliJ平台Gradle插件
    alias(libs.plugins.changelog) // Gradle更新日志插件
    alias(libs.plugins.qodana) // Gradle Qodana插件
    alias(libs.plugins.kover) // Gradle Kover插件
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

// 设置构建项目使用的JVM语言级别
kotlin {
    jvmToolchain(21)
}

// 配置项目依赖项
repositories {
    mavenCentral()

    // IntelliJ平台Gradle插件仓库扩展 - 更多信息：https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

// 依赖项使用Gradle版本目录管理 - 更多信息：https://docs.gradle.org/current/userguide/version_catalogs.html
dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.opentest4j)

    // IntelliJ平台Gradle插件依赖项扩展 - 更多信息：https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea(providers.gradleProperty("platformVersion"))

        // 插件依赖项。使用gradle.properties文件中的`platformBundledPlugins`属性来指定捆绑的IntelliJ平台插件。
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // 插件依赖项。使用gradle.properties文件中的`platformPlugins`属性来指定来自JetBrains Marketplace的插件。
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        // 模块依赖项。使用gradle.properties文件中的`platformBundledModules`属性来指定捆绑的IntelliJ平台模块。
        bundledModules(providers.gradleProperty("platformBundledModules").map { it.split(',') })

        testFramework(TestFrameworkType.Platform)
    }
}

// 配置IntelliJ平台Gradle插件 - 更多信息：https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")

        // 从README.md中提取<!-- Plugin description -->部分并提供给插件清单
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("在README.md中未找到插件描述部分：\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        val changelog = project.changelog // 用于配置缓存兼容性的局部变量
        // 从更新日志文件中获取最新的可用变更说明
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // pluginVersion基于SemVer（https://semver.org）并支持预发布标签，如2.1.7-alpha.3
        // 指定预发布标签以自动在自定义发布渠道中发布插件。更多信息：
        // https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html#specifying-a-release-channel
        channels = providers.gradleProperty("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

// 配置Gradle更新日志插件 - 更多信息：https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
}

// 配置Gradle Kover插件 - 更多信息：https://kotlin.github.io/kotlinx-kover/gradle-plugin/#configuration-details
kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    publishPlugin {
        dependsOn(patchChangelog)
    }
}

intellijPlatformTesting {
    runIde {
        register("runIdeForUiTests") {
            task {
                jvmArgumentProviders += CommandLineArgumentProvider {
                    listOf(
                        "-Drobot-server.port=8082",
                        "-Dide.mac.message.dialogs.as.sheets=false",
                        "-Djb.privacy.policy.text=<!--999.999-->",
                        "-Djb.consents.confirmation.enabled=false",
                    )
                }
            }

            plugins {
                robotServerPlugin()
            }
        }
    }
}