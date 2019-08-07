#!groovy
@Library('metaborg.jenkins.pipeline@develop') _

spoofaxCoreLanguagePipeline(
  upstreamProjects: ['/metaborg/spoofax-releng/master'],
  mavenGlobalSettingsFilePath: '.mvn/settings.xml',
  mavenSettingsConfig: 'metaborg-deploy-maven-config',
  slack: true
)
