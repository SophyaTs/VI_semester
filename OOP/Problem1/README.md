How to install and start ActiveMQ: https://websiteforstudents.com/how-to-install-apache-activemq-on-ubuntu-20-04-18-04/

If you encounter an error like this:
```
activemq[117327]: ERROR: Configuration variable JAVA_HOME or JAVACMD is not defined correctly.
activemq[117327]:        (JAVA_HOME='', JAVACMD='java')
```
Then try this: https://www.fatalerrors.org/a/configuration-variable-java_home-or-javacmd-is-not-defined.html

To run the project, you need to add activemq jar. There is activemq-all-version.jar in its installation directory, add it and everything will work!
