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


**COMMANDS**
- /confirm - Opens the package claim menu.
- /givepackage <player> <package id> <transaction id> - Adds a package to a players confirmation meny. Package id would be whatever you setup in the config.yml.
- /atlasconfirm reload - Reloads the config.yml

**PERMISSIONS**
- atlasconfirm.givepackage - Permission to run the /givepackage command.
- atlasconfirm.reload - Permission to run the /atlasconfirm reload comand

**CONFIG**
**NOTICE:** Please be sure when configuring the plugin to have all of your package id's/names lowercase.

```
########################################################################################################
########## AtlasConfirm v1.0 developed by Classified Dev. Discord: https://discord.gg/c9BCaNn ##########
########################################################################################################
inventory:
  title: '&8Buycraft Package Claim'
  package-lore:
    - '&7Click item to claim this Buycraft package.'
    - ' '
    - '&c&lNOTE: &7If you do &7&nNOT&r &7wish to accept'
    - '&7this package, please right-click it.'
    - ' '
    - '&4&lWARNING: &7By accepting this package'
    - '&7you claim full responsibility for the'
    - '&7payment and you will be banned if it is'
    - '&7flagged as fraudulent or if the payment'
    - '&7is charged-back by PayPal.'
    - ' '
    - '&3&lPACKAGE ID: &7%package_id%'
    - '&3&lTRANSACTION ID: &7%transaction_id%'

messages:
  no-permission: '&c&l(!) &cYou do not have permission to use this command!'
  player-not-found: '&c&l(!) &cA player with that name could not be found!'
  package-not-found: '&c&l(!) &cA package with that name could not be found!'
  reload: '&6&l(!) &6Atlas Confirm config.yml has been reloaded.'
  atlasconfirm-usage:
    - '&c&l(!) &cUsages: &7/atlasconfirm reload'
    - '&8&l* &7Reloads the Atlas Confirm config.yml.'
  givepackage-usage:
    - '&c&l(!) &cUsage: &7/givepackage <player> <package> <transaction id>'
    - '&8&l* &7Sends a player a Buycraft package for them to claim with /confirm.'
  confirm-notify: #This message is sent to the player when they have an available package to claim.
    - ''
    - '&8&l(&a&l*&8&l) &a&lYOU HAVE A BUYCRAFT PACKAGE TO CLAIM!'
    - '&8&l* &7Please type /confirm to claim your packages.'
    - ''
 
  confirm-remaining: #This message is sent to the player when they claim a package and they still have packages remaining to claim.
    - ''
    - '&8&l(&6&l*&8&l) &6&lYOU STILL HAVE REMAINING BUYCRAFT PACKAGE(S) TO CLAIM!'
    - '&8&l* &7Please type /confirm to claim your packages.'
    - ''

  no-packages: #This message is sent to the player when they attempt to run the /confirm command and there is nothing available to claim.
    - '&c&l(!) &cYou do not have any available Buycraft packages to claim!'
    - '&8&l* &7If you just purchased a package please wait up to 15 minutes to receive it in-game.'

  package-accepted: #This message is sent to the player when they accept a package.
    - '&8&l(&a&l*&8&l) &a&lYOU HAVE &a&l&nACCEPTED&r &a&lTHE BUYCRAFT PACKAGE: &3&l%package_name%'

  package-denied: #This message is sent to the player when they deny a package.
    - '&8&l(&c&l*&8&l) &c&lYOU HAVE &c&l&nDENIED&r &c&lTHE BUYCRAFT PACKAGE: &3&l%package_name%'

  donation: #This message is sent to the whole server when a player accepts a package.
    - '&8&l(&3&l*&8&l) &3%player% &7has donated at &3&nshop.yourserver.com &7with &c&l25% OFF SALE!'

# Package configuration section. You can add as many packages as you'd like. Just copy and paste the format.
packages:
  diamond:    (Lowercase)     #Name of package used in givepackage command. Ex: /givepackage Steve hero TRANSACTIONID
    name: '&3&l32x Diamonds'            #Name of package in /confirm GUI
    commands:                           #Commands to be run when package is claimed.
      - 'give %name% diamond 32'
```
