import net.kyori.indra.git.IndraGitExtension
import net.kyori.indra.git.internal.IndraGitExtensionImpl
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class ReleaseTask : DefaultTask() {
    
    @TaskAction
    fun release() {
        val git = project.extensions.findByType(IndraGitExtension::class.java)?.git()?: return
        
        val currentVersion = project.versionString()
        
        val projectName =  project.providers.gradleProperty("projectName").orElse(project.nameString(false))
        
        val versionNoSnapshot = currentVersion.replace("-SNAPSHOT", "", ignoreCase = true)
        
        // set version without snapshot in properties file
        project.rootProject.file("gradle.properties").writeText(
            project.rootProject.file("gradle.properties").readText().replaceFirst("-SNAPSHOT$", "")
        )
        project.version = versionNoSnapshot
        
        git.add().addFilepattern("gradle.properties").call()
        git.commit().setMessage("release: $versionNoSnapshot").call()
        
    }
}