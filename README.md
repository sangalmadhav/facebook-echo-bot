# Facebook echo bot using Vert.x 

This project shows how to deploy a facebook echo bot Vert.x 3 applications to Heroku. The same application can be deployed using 3 approaches:

* Using a one click badge
* Using the maven plugin
* Using the git interface

If you use a fat-jar then deploying on heroku is as simple as one click. The only requirement is to create the Heroku specific [Procfile](../Procfile) with a `Dyno` of type web.


## Use the Heroku Toolbelt

Follow these steps, after installing the [Heroku Toolbelt](https://toolbelt.heroku.com/), to deploy with Maven:

```sh-session
$ git clone https://github.com/jboss-outreach/facebook-echo-bot.git
$ heroku create
$ mvn package heroku:deploy
```



When creating a project of your own, you'll need to borrow from the [`Procfile`](https://github.com/jboss-outreach/facebook-echo-bot/blob/master/Procfile) in the root directory of this project, and the `MAVEN_CUSTOM_OPTS` will only be necessary if your app is not the primary module of your Maven project.
