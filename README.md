# ![AtlasConfirm](https://i.imgur.com/W9WDmkC.png)
A package confirm/claim system, created for Buycraft. 


AtlasConfirm is a plugin that should be used in combination with a monetization service such as Buycraft. It is used as a confirmation system to prevent unauthorized purchases on a player's account. When a player purchases an item off of your server store instead of instantly receiving the item in their inventory they will be prompted to run the /confirm command which will open a menu showing the packages they have purchased.

![AtlasConfirm](https://i.imgur.com/UuJZSka.png)
![AtlasConfirm](https://i.imgur.com/UPP5swt.png)
![AtlasConfirm](https://i.imgur.com/qy5Qv1b.png)

They then will need to either left-click the package to confirm they made the purchase and receive their goods or alternatively right-click the package to deny it which will delete the items they would have gotten originally.
![AtlasConfirm](https://i.imgur.com/eAl9J40.png)
![AtlasConfirm](https://i.imgur.com/kP6oG20.png)

Running the /confirm command when there are no packages to claim will simply display this message.
![AtlasConfirm](https://i.imgur.com/PAW96fh.png)

Denying a package does not reverse the payment as this plugin does not actually connect with Buycraft in any way. All confirmations and denials of packages are logged to a file in the plugins folder, examples of these log entries can be seen below.

![AtlasConfirm](https://i.imgur.com/wSMh3n8.png)

In order to set this plugin up simply configure the messages to your liking and add whatever packages you choose to the config.yml. After you finish editing the config change your Buycraft package commands to the /givepackage command as shown below.

![AtlasConfirm](https://i.imgur.com/nTw4Akv.png)

And there you go! You're all set up! Now instead of your players instantly getting their items, they will need to claim them from the confirmation menu.


COMMANDS
- /confirm - Opens the package claim menu.
- /givepackage <player> <package id> <transaction id> - Adds a package to a players confirmation meny. Package id would be whatever you setup in the config.yml.
- /atlasconfirm reload - Reloads the config.yml

PERMISSIONS
- atlasconfirm.givepackage - Permission to run the /givepackage command.
- atlasconfirm.reload - Permission to run the /atlasconfirm reload comand
