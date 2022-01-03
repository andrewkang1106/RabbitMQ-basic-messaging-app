# RabbitMQ Implementation
This simple implementation was created as I learned RabbitMQ.

-Current implementation allows messages to be produced (explicitly written in the code) and consume them.

-These can be tested and checked in localhost: 15672 w/  
UserID: guest  
Password: guest

Classes are set up so that:  
* Producer.java works with Consumer.java  
* NewTask.java works with Worker.java  
* EmitLog.java works with ReceiveLogs.java


Producer - Consumer  (Nameless exchange)
* -implemented basic functionality so that messages can be sent and received.

NewTask - Worker (Work Queue with default exchange)
* implemented round-robin work queue so that multiple workers can pick up messages from the queue in sequential order (still one task per worker).

EmitLog - ReceiveLogs (Publish/Subscribe - Fanout exchange)
* Implemented simple logging system in which a message will be delivered to multiple consumers

Routing (Receiving messages selectively)
