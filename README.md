# RabbitMQ Implementation
This simple implementation was created as I learned RabbitMQ.

-Current implementation allows messages to be produced (explicitly written in the code) and consume them.

-These can be tested and checked in localhost: 15672 w/  
UserID: guest  
Password: guest

Classes are set up so that:  
Producer.java works with Consumer.java  
NewTask.java works with Worker.java  


Producer - Consumer  
* -implemented basic functionality so that messages can be sent and received.

NewTask - Worker  
* implemented round-robin dispatch so that multiple workers can pick up messages from the queue in sequential order.