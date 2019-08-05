#!groovy
@Library('metaborg.jenkins.pipeline@develop') _

spoofaxCoreLanguagePipeline(
  upstreamProjects: ['/metaborg/spoofax-releng/cs4200'], 
  mavenGlobalSettingsFilePath: '.mvn/settings.xml',
  slackNotify: true,
  slackNotifyChannel: "#cs4200"
)
