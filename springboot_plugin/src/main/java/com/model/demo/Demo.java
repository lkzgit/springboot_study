package com.model.demo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**  https://maven.apache.org/guides/plugin/guide-java-plugin-development.html
 * 自定义插件
 */
@Mojo(name="pluginDemo",defaultPhase = LifecyclePhase.PACKAGE)
public class Demo  extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("自定义插件demo");
    }
}
