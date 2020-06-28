Here are some use cases.

***
**Build a VIP world**
- Create a world called 'myWorld';
- Issue '/wp config add myWorld';
- Give VIP Group the permission 'nworldpermissions.forfreeto.myWorld';
- Done.

***
**Teleport the players out of a world**
- Make sure the offline players tracker has been set correctly; (see [Offline Players Tracker](https://github.com/yueyinqiu/NWorldPermissions/wiki/Offline-Players-Tracker))
- Issue '/wp marks add myMark' to set a mark;
- Issue '/wp tp offline myWorld myMark' to teleport all the online players in the world 'myWorld' to the mark;
- Issue '/wp tp online myWorld myMark' to teleport all the online players in that world to the mark;
- Issue '/wp tp online myWorld myMark' to avoid '/back';
- Issue '/wp tp offline myWorld myMark' to make sure all the players has teleported;
- Done.
