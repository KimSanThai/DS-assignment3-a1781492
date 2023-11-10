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
-M2: responds very late or extremely fast (0-10 seconds)
-M3: not as responsive as M1 but not as late as M2 (3-6 seconds)
-M4-M9: random response time (0-5 seconds)

Proposer:
The proposer has a list of ports he sents proposals to and all response sent back are saved to a concurrentHashMap. The hash map needs to be thread safe as the messages are sent all at once using Threads so the response are added back in the same way.
Once all the messages are sent out, it checks if a majority of the acceptor has sent a promise message back then checks if the promise message contains any accepted values.
If there exists an accepted value, then it saves it to its own value along with the accepted proposal number. After this a propose message is sent to which an accept message is expected back.
Once enough accepted message is sent back with the same message then a consensus message is sent to everyone.