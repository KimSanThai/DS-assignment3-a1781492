//Kim San Thai - a1781492

Design Brief:

Implement Paxos algorithm implement to simulate the Adelaide Suburbs Council Election. Paxos implementation is based upon Paul Krzyanowski (https://people.cs.rutgers.edu/~pxk/417/notes/paxos.html).
The implementation will use a decentralized design where messages are sent directly peer to peer. The major components for this code is the Acceptor and Proposer.

For Psuedo-Code refer to website above.

Implementation:
Acceptor:
The Acceptor opens a ServerSocket to receive messages from proposers on port based on their ID. This can be manually set in the member files (M*.java) by altering the port variable.
Depending on the received class type of the received message, the acceptor responds accordingly.
If prepare, send promise. If propose, send accept. If consensus, then end thread.
The acceptor also has a delay function implemented that generates a random value to delay the response time to mimic the design brief requirements.
-M1: no delays
-M2*: responds very late or extremely fast (0-10 seconds)
-M3**: not as responsive as M1 but not as late as M2 (3-6 seconds)
-M4-M9: random response time (0-5 seconds)

*M2 has been set to have a 50% chance to just ignore message sent to it
**M3 has been set to have a 20% chance to not respond to message at all (Acceptor never starts)
***A Proposer will always be started regardless of messages being ignored or not

Proposer:
The proposer has a list of ports he sents proposals to and all response sent back are saved to a concurrentHashMap. The hash map needs to be thread safe as the messages are sent all at once using Threads so the response are added back in the same way.
Once all the messages are sent out, it checks if a majority of the acceptor has sent a promise message back then checks if the promise message contains any accepted values.
If there exists an accepted value, then it saves it to its own value along with the accepted proposal number. After this a propose message is sent to which an accept message is expected back.
Once enough accepted message is sent back with the same message then a consensus message is sent to everyone.
*Proposers starts a new round of messages every 20 seconds and will move on if response is not received every 10 seconds.
**Number of messages are kept track of by storing the messages received in a ConcurrentHashMap that is reset at the start of every new round.

Usage:
Compile with command "javac *.java" or "make"
All members can be called with command "java M* <true|false>"
The true or false determines whether the delay is added to each member (including the disconnects for M2 and M3).
for M2 and M3, command "java M* fail" can also be used to run a modified version of the Proposer class that sends out a propose message then disconnects.

Testing:
Ideally, each member is run on its own terminal so that outputs can be seen clearly.
However, due to testing the output if M1-M3 were run at the same time (testing concurrent prepare message sending) Test1.sh script run M1-M3 simultaneously.
As for the Acceptors (M4-M9), for optimal viewability, it should be ran manually on its own terminal. However, for testing purposes Test2.sh will start all the Acceptors (excluding M1-M3 which requires Test1.sh to start).
-Test1.sh and Test2.sh are used for "NO DELAY TESTING" - see lines above for details.
Test1.sh can be run with "./Test1.sh"
Test2.sh can be run with "./Test2.sh"
*May require permission to be run as an executable

Similarly, for the delay testing it is advised that the members are manually called one by one on their own terminal for visibility (using command "java M* true").
However, for testing purposes Test3.sh script will be provided that does what Test1.sh does but with the delay enabled.
In the same way, Test4.sh will do what Test3.sh does but with the delay enabled.
-Test3.sh and Test4.sh are used for "DELAY TESTING" - see lines above for details.
Test3.sh can be run with "./Test3.sh"
Test4.sh can be run with "./Test4.sh"
*May require permission to be run as an executable

Test5.sh can be used to run the fail version of M2 and M3 where they will both send propose message then go offline. This can be used in conjunction with either Test2.sh or Test4.sh (depending on if delay needs to be implemented).
Test5.sh can be run with "./Test5.sh"
*May require permission to be run as an executable
**Delays are turned off for M1-M3 Acceptors this can be modified in the java file manually by changing true false in their respective java files (M1 will have to be changed in the script file - line 1).
(M2 and M3 delay for this testing has to be changed manually in their java files under line 38 and line 47 respectively)

Output:
As the delays are randomized, the outputs are randomized but a consensus should be reached. The voting process can be followed by looking at the output (which is why manual code testing is recommended).
As for Test5.sh, as the propose message is already sent out, it may be up for M1 to receive the accepted promise value then declare it as the consensus value once the consensus number of accept message has been achieved.