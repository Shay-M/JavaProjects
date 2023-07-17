// package shay.finalproject.webcraeler.event;
//
// import org.springframework.context.ApplicationListener;
// import org.springframework.context.event.ContextStartedEvent;
// import org.springframework.context.event.EventListener;
// import org.springframework.stereotype.Component;
//
// // @Component
// // public class DataReadyEventListener implements ApplicationListener<DataReadyEvent> {
// //     @Override
// //     public void onApplicationEvent(final DataReadyEvent event) {
// //         System.out.println("Received spring custom event - " + event.getMessage());
// //
// //     }
// //
// // }
//
// @Component
// public class DataReadyEventListener {
//     @EventListener
//     public void handleContextStart(final ContextStartedEvent cse) {
//         // Do something when the event occurs
//         System.out.println("Handling context started event." + cse);
//     }
// }