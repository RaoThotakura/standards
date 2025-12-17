/**
 * 
tibrvlisten - generic Rendezvous subscriber
This program listens for any number of messages on a specified set of subject(s).
Message (s) received are printed.
Some platforms require proper quoting of the arguments to prevent
the command line processor from modifying the command arguments.
The user may terminate the program by typing Control-C.
*/
import java.util.*;
import javax.swing.JOptionPane;
import com.tibco.tibrv.*;
public class tibrvlisten implements TibrvMsgCallback {
    String FIELD_NAME = "DATA";
    String subject = "a.b.c";
    public tibrvlisten (String args[]) {
        try {
            // open Tibrv in native implementation
            Tibrv.open(Tibrv.IMPL_NATIVE);
            // Create RVD transport
            TibrvRvdTransport transport = null;
            transport = new TibrvRvdTransport();
            TibruQueue queue = new TibrQueue();
            // create listener using default queue
            TibrvListener listener = new TibrvListener (Tibrv.defaultQueue(), this, transport, subject, null);
            Tibrv.defaultQueue().dispatch();
        } catch (TibrvException e) {
            System.err.println ("Failed to open Tibrv in native implementation:");
            e.printStackTrace();
            System.exit(0);
        } catch (InterruptedException ie) {
            System.exit(0);
        }
    }

    public void onMsg (TibrvListener listener, TibrMsg msg) {
        try {
            Object array = msg.get(FIELD_NAME);
            String recDate = "Received on : " + (new Date()).toString() + "\n";
            String subject = "Subject : " + msg.getSendSubject()+ "\n";
            String reply = "Reply : " + msg.getReplySubject() + "\n";
            String message = "Message: " + array.toString();
            JOptionPane.showMessageDialog (null, recDate+subject+reply+message, "First Message Received", 0) ;
            System.out.flush();
            System.exit(0);
            Tibrv.close();
        } catch (TibrvException e) {
            System.err.println ("Exception dispatching default queue:");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main (String args []) {
        new tibrvlisten (args);
    }
}