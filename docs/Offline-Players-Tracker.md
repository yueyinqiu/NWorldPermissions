Here is the introduction of the 'offline-players-tracker' in 'config.yml' and the command '/wp tp offline'.

You should simply learn how it works or you may face some problems.
***
**Implementation**

When 'offline-players-tracker.enabled' is 'true', every time a player exit the game, the position will be recorded, and the player joining the game will be teleported here for 'offline-players-tracker.teleport-times.position-unchanged' times.

You can easily find that if you don't set 'offline-players-tracker.enabled' to 'true' in advance, some players might be missed. _So, if you are not sure, always keep it 'true'._

And after an operator issuing '/wp tp offline', the record will be changed, then when the players join the game, they will be teleported for 'offline-players-tracker.teleport-times.position-changed' times.

The 'teleport-times' is used to set how many times should the player get teleported, you can read on and understand what it's designed for.

***
**Basic Functions**

If you just want to teleport the offline players, it will work if you set the tracker like this:

```yaml
offline-players-tracker:
  enabled: true
  teleport-times:
    position-changed: 1
    position-unchanged: 0 #They won't be teleported.
```

And when you want to teleport all the players out a world, you may issue commands like this way to make sure all players are teleported:
- /wp tp offline <world> <mark>
- /wp tp online <world> <mark>
- /wp tp online <world> <mark> -> Avoid '/back'.
- /wp tp offline <world> <mark> -> Players may leave the game between the step 1 to 2, so they may haven't gotten teleported, and you should also issue this command quickly, or they might get online again...

***
**More Functions**

Setting 'teleport-times' bigger could avoid the commands like '/back'.

Don't worry, they can't use '/back' to get back if the world is controlled. But after the world is uncontrolled, '/back' might teleport them to a place where you don't want them to get in, so the function came into being.

If you're using 'Essentials' and want this feature, set 'register-back-in-listener' to 'true' (in 'Essentials'), otherwise it won't be recorded by 'Essentials'. Then you may set the tracker like this:
```yaml
offline-players-tracker:
  enabled: true
  teleport-times:
    position-changed: 2 #Teleport out firstly, then teleport again to change the '/back' postion.
    position-unchanged: 1 #Just change the '/back' postion.
```