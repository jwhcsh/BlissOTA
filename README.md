# Maintainers, your ROM must be set up to include these or else it would not work properly:

1. A place to host your ROM addons to be placed into ota.xml (Default goes to http://downloads.blissroms.com/Add-ons/addons.xml but you can make your own)
3. A place to host your update info (Add it into http://downloads.blissroms.com/BlissPop/Official/modelname/ota.xml)
3. Build.prop entries (To ensure compatibility/non-compatible dialogs from showing up)

# OTA.XML Template

As of April 1st, 2015, the Bliss build code will have ota.xml support to structure it for you, as well as place it in your correct directory.

# ADDONS.XML Template

This file needs to be added into http://downloads.blissroms.com/Add-ons/, please look at http://downloads.blissroms.com/Add-ons/addons.xml on how to structure it. You can make your own. The final link will be added into the bottom of ota.xml

# BUILD.PROP Entries

If you haven't already, please merge http://review.blissroms.com:8081/#/c/78/ to get the build.prop entries "ro.ota.systemname, ro.ota.version, ro.ota.device, ro.ota.manifest"

# Instructions to compile this app yourself

https://github.com/Kryten2k35/OTAUpdates/blob/stable/README.md

# Licencing

This project is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0). A copy of the licence can be obtained [here](http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode).

This licence is chosen because it gives people the rights to clone this project and freely use it in their ROM or application, but only so long as they are sharing it in the same manner, and not going to publish it for commercial uses. I.E. This is free to use and modify (so long as you share any changes under the same licence), but you can't take it and sell it.

Other parts of this project (Bypass, cardsview) have their own licences and are not affected by this one.