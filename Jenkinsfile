#!groovy
@Library('metaborg.jenkins.pipeline@develop') _

spoofaxCoreLanguagePipeline(
  upstreamProjects: ['/metaborg/spoofax-releng/cs4200'], 
  mavenGlobalSettingsFilePath: '.mvn/settings.xml',
  slack: true,
  slackChannel: "#cs4200"
)
