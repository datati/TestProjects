TestProjects
============

FrontLineSMSuser: An uptime monitoring webapp

This is an application that polls the "https://cloud.frontlinesms.com" site and alerts the on-call developer of any downtimes via SMS alerts(effected through FrontlineSMS). The on-call developer (gotten from a schedule of people on support stored in a database) can then send a reply SMS through FrontlineSMS after resolving/investigating the incident.

Not implemented:
1.The poller (using Quartz) will run every 30 minutes.
2.A database of people on support, with their names and mobile numbers.(Currently using a form to get that information)
3. Sending a POST request with a json body to the sync https URL. Getting a 500 error on this:(


Check out the project as a Maven project in eclipse, deploy to container - preferably TomaCat 6.0.
Run on the browser:
http://localhost/FrontLineSMSuser/welcome/
