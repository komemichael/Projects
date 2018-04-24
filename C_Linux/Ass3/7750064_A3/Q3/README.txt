Make the .out file for main program by calling make, this will create an object run.
Make the .out file for shutdown.c by typing "gcc -o shut shutdown"

Run the program by typing "./run" or "run"

The program will pause for a while and then continue during the execution process because of the 
sleep calls which indicates printing.

Observe the output. Client puts print request in the buffer.

Server retrieves requests in the buffer and this is done asynchronously.

After printing the Print Requests, the server cannot retrieves from an empty

buffer so it waits on the mutex. Tke note or copy the numeric part of the printer ID.

At this point open a new bash console in the same directory. Run the shutdown process by
typing "./shut" or "shut" with an arguement of the printer ID copied. For example, a run 
would look like "shut 55555" if the server ID was 55555.




