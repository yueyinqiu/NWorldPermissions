You can check the plugin(4.2.0)'s availability through the following steps (and you can learn how to use it in the process). Please proceed in strict order.

***
**Messages and Command Permissions Part**
- Issue '/nworldpermissions'. Have you received a message?
- Issue '/wp'. Have you received the same message? (If this command is occupied, you can try '/nwp', '/nwdp' or '/worldpermissions')
- Get the operator identity;
- Edit the file 'messages.yml' through a text editor, changing the value of 'messages.help';
- Issue '/wp reload messages'. Have you received a message?
- Issue '/wp'. Is the message changed?
- Cancel your operator identity;
- Issue '/wp config', '/wp marks', '/wp tp' and '/wp reload'. Is there nothing happened?
- Get the permissions as '/wp' shows, issue them again. Are they available now? (Don't forget to remove these permissions for other players after the test, you can do it now, we won't need them in the next steps)

***
**Stop Teleporting Part**
- Edit the file 'config.yml', adding a controlled world, and setting 'offline-players-tracker.enable' to 'true', 'offline-players-tracker.teleport-times.position-changed' to '2', 'offline-players-tracker.teleport-times.position-unchanged' to '1';
- Get the operator identity;
- Issue '/wp reload config'.
- If you are in the controlled world, teleport to another one;
- Cancel your operator identity and make sure that you don't have the permission 'nworldpermissions.forfreeto.<controlled_world>';
- Try to teleport to the controlled world. Have you got stopped and received a message? (you can just issue '/mv tp <world_name>' by the console if you are using the 'multiverse' plugin)
- Give yourself the permission 'nworldpermissions.forfreeto.<controlled_world>';
- Try to teleport to the controlled world again. Have you successfully teleported here and received a message?
- Get the operator identity;
- Set the controlled worlds by issuing '/wp config add' and '/wp config remove'. Are they available? (you can just check it by monitoring the file 'config.yml' instead of teleporting yourself)

***
**Marks Part**
- Add some marks by issuing '/wp marks add'. Is it available? (monitoring the file 'marks.yml')
- Remove some of them by issuing '/wp marks remove'. Is it available?
- Try to write the file 'marks.yml' then issue '/wp reload marks'. Is it available?

***
**Online Teleport Part**
- Issue '/wp tp online <the_world_you_are_in> <an_existed_mark>'. Have you gotten teleported?

***
**Offline Part**
- Exit the game, is there a new directory called 'playersData'?
- Is there a file called '<your_uuid>.yml' in the directory 'playersData'?
- Is there a location(with the key 'position') and a boolean value 'false'(with the key 'changed') recorded in the file?
- Change the position; (you may change the world to make it more obvious)
- Join the game. Dose your location changed? (you should disable the similar functions of other plugins in advance, such as 'Authme''s teleporting to the spawn when join)
- Issue '/back' or other similar commands (which aims to teleport to a previous location). Is it available? (if players can't use '/back', just skip this step)
- Teleport to another world then exit the game;
- Issue '/wp tp offline <the_world_you_are_in> <an_existed_mark>' (by other operators or by console). Does 'position' in 'playersData/<your_uuid>.yml' changed to the mark? Does 'changed' in 'playersData/<your_uuid>.yml' changed to 'true'?
- Join the game. Dose your location changed?
- Issue '/back' or other similar commands. Does it just bring you to the same location now? (because you've teleported twice) (if you're using 'Essentials', set 'register-back-in-listener' to 'true' (in 'Essentials') in advance)

***
If all the answers are 'yes', your game version is fully supported, and please tell me that I can add it to the document; If not, you can also contact me and give me some details, that I may modify the plugin to be compatible with your version.