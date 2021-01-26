package Windows;

import Application.Consumer;
import Helpers.FriendRequest;
import Helpers.Notification;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class InboxPage extends JFrame {
    private JPanel MainPanel;
    private JScrollPane NotificationsPane;
    private JScrollPane FriendRequestsPane;
    private JButton declineButton;
    private JButton acceptButton;
    private JLabel FriendRequestsLabel;
    private JLabel NotificationsLabel;
    private JList notifications;
    private JList friendRequests;
    public InboxPage(Consumer user, ProfilePage parent) {
        super("Inbox");
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        update(user);
        acceptButton.setEnabled(false);
        declineButton.setEnabled(false);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendRequest selected = (FriendRequest) friendRequests.getSelectedValue();
                user.acceptFriendRequest(selected);
                update(user);
                parent.update(user);
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendRequest selected = (FriendRequest) friendRequests.getSelectedValue();
                user.declineFriendRequest(selected);
                update(user);
                parent.update(user);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    // function to update the current page (didn't use default list model)
    public void update(Consumer user) {
        Vector<Notification> notificationVector = new Vector<>(user.getInbox());
        notifications = new JList(notificationVector);
        NotificationsPane.setViewportView(notifications);

        Vector<FriendRequest> friendRequestVector = new Vector<>(user.getFriendRequests());
        friendRequests = new JList(friendRequestVector);
        // added a list selection listener to be able to enable buttons if i need to
        friendRequests.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (friendRequests.isSelectionEmpty()) {
                    acceptButton.setEnabled(false);
                    declineButton.setEnabled(false);
                }
                else {
                    acceptButton.setEnabled(true);
                    declineButton.setEnabled(true);
                }
            }
        });
        FriendRequestsPane.setViewportView(friendRequests);
    }
}
