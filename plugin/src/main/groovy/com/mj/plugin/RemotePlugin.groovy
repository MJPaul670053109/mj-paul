package com.mj.plugin


import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project

class RemotePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        project.extensions.create("remoteExtension", RemoteExtension, project)

        def transform = new MyClassTransform(project)
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(transform)

        if (project.plugins.hasPlugin(AppPlugin)) {
            android.applicationVariants.all { variant ->
                def variantData = variant.variantData
                def scope = variantData.scope
                //拿到build.gradle中创建的Extension的值
                def config = project.extensions.getByName("remoteExtension")
                project.task(scope.toString() + "javassistFile") {
                    group 'remotetasks2'
                    doLast {
                        //生成java类
                        createJavaTest(variant, config)
                    }
                }
            }
        }

        project.task('remote') {
            dependsOn 'assembleDebug'
            group 'remotetasks2'
            doLast {
                RemoteExtension extension = project.extensions.findByName("remoteExtension")
                println extension.aa
                println extension.bb

                extension.domain.all { Domain data ->
                    println data.msg
                    println data.name
                }
            }
        }
    }

    static void createJavaTest(ApplicationVariant variant, RemoteExtension config) {

        //要生成的内容
        def content = """
package com.mj.plugin;
public class RemotePluginTestClass {
        public static final String str = "${config.str}";
}
"""
        //获取到BuildConfig类的路径
        File outputDir = variant.getVariantData().getScope().getBuildConfigSourceOutputDir()
        def javaFile = new File(outputDir, "RemotePluginTestClass.java")
        javaFile.write(content, 'UTF-8')
    }
}
