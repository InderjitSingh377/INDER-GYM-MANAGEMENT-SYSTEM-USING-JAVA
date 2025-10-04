import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

class Member {
    int id;
    String name;
    int age;
    String membershipType;

    Member(int id, String name, int age, String membershipType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
    }
}

public class GymManagementGUI extends JFrame {
    private JTextField txtId, txtName, txtAge;
    private JComboBox<String> cmbMembership;
    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Member> members = new ArrayList<>();

    public GymManagementGUI() {
        setTitle("INDER GYM MANAGEMENT SYSTEM");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Labels and Inputs
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(120, 20, 150, 25);
        add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 60, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(120, 60, 150, 25);
        add(txtName);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(20, 100, 100, 25);
        add(lblAge);
        txtAge = new JTextField();
        txtAge.setBounds(120, 100, 150, 25);
        add(txtAge);

        JLabel lblMembership = new JLabel("Membership:");
        lblMembership.setBounds(20, 140, 100, 25);
        add(lblMembership);
        String[] memberships = { "Monthly", "Quarterly", "Yearly" };
        cmbMembership = new JComboBox<>(memberships);
        cmbMembership.setBounds(120, 140, 150, 25);
        add(cmbMembership);

        // Buttons
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 190, 80, 30);
        add(btnAdd);
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 190, 80, 30);
        add(btnUpdate);
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(200, 190, 80, 30);
        add(btnDelete);

        btnAdd.setBackground(Color.BLACK);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false); // Removes the focus box when clicked

        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false); // Removes the focus box when clicked

        btnDelete.setBackground(Color.BLACK);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false); // Removes the focus box when clicked

        // Table to show members
        String[] columnNames = { "ID", "Name", "Age", "Membership" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300, 20, 370, 350);
        add(scrollPane);

        // Button actions
        btnAdd.addActionListener(_ -> addMember());
        btnUpdate.addActionListener(_ -> updateMember());
        btnDelete.addActionListener(_ -> deleteMember());

        // Table row select to fill form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtId.setText(model.getValueAt(selectedRow, 0).toString());
                    txtName.setText(model.getValueAt(selectedRow, 1).toString());
                    txtAge.setText(model.getValueAt(selectedRow, 2).toString());
                    cmbMembership.setSelectedItem(model.getValueAt(selectedRow, 3).toString());
                    txtId.setEnabled(false);
                }
            }
        });
    }

    private void addMember() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String membership = cmbMembership.getSelectedItem().toString();

            for (Member m : members) {
                if (m.id == id) {
                    JOptionPane.showMessageDialog(this, "Member ID already exists!");
                    return;
                }
            }

            Member member = new Member(id, name, age, membership);
            members.add(member);
            model.addRow(new Object[] { id, name, age, membership });
            clearFields();
            JOptionPane.showMessageDialog(this, "Member added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID and Age.");
        }
    }

    private void updateMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());
                String membership = cmbMembership.getSelectedItem().toString();
                int id = Integer.parseInt(txtId.getText());

                for (Member m : members) {
                    if (m.id == id) {
                        m.name = name;
                        m.age = age;
                        m.membershipType = membership;
                        break;
                    }
                }

                model.setValueAt(name, selectedRow, 1);
                model.setValueAt(age, selectedRow, 2);
                model.setValueAt(membership, selectedRow, 3);

                JOptionPane.showMessageDialog(this, "Member updated successfully!");
                clearFields();
                txtId.setEnabled(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Age.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to update.");
        }
    }

    private void deleteMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            members.removeIf(m -> m.id == id);
            model.removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Member deleted successfully!");
            clearFields();
            txtId.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.");
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        cmbMembership.setSelectedIndex(0);
        txtId.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymManagementGUI app = new GymManagementGUI();
            app.setVisible(true);
        });
    }
}