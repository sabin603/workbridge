package ui;

import dao.ProfileDAO;
import model.User;
import model.WorkerProfile;

import javax.swing.*;
import java.awt.*;

public class ProfileForm extends JFrame {

    private User jobSeeker;

    private ProfileDAO profileDAO;

    private JTextField addressField;

    private JTextField phoneField;

    private JTextField pictureField;

    private JTextArea skillsArea;

    private JLabel nameLabel;

    private JLabel emailLabel;


    public ProfileForm(User user) {

        this.jobSeeker = user;

        profileDAO = new ProfileDAO();

        setTitle(
                "WorkBridge - My Profile"
        );

        setSize(600, 600);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadProfile();
    }


    private void createUI() {

        JPanel panel =
                new JPanel();

        panel.setLayout(null);


        JLabel titleLabel =
                new JLabel("My Profile");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setBounds(
                230,
                20,
                150,
                35
        );

        panel.add(titleLabel);


        // NAME

        JLabel nameTextLabel =
                new JLabel("Name:");

        nameTextLabel.setBounds(
                60,
                80,
                100,
                25
        );

        panel.add(nameTextLabel);


        nameLabel =
                new JLabel(
                        jobSeeker.getName()
                );

        nameLabel.setBounds(
                180,
                80,
                350,
                25
        );

        panel.add(nameLabel);


        // EMAIL

        JLabel emailTextLabel =
                new JLabel("Email:");

        emailTextLabel.setBounds(
                60,
                120,
                100,
                25
        );

        panel.add(emailTextLabel);


        emailLabel =
                new JLabel(
                        jobSeeker.getEmail()
                );

        emailLabel.setBounds(
                180,
                120,
                350,
                25
        );

        panel.add(emailLabel);


        // ADDRESS

        JLabel addressLabel =
                new JLabel("Address:");

        addressLabel.setBounds(
                60,
                160,
                100,
                25
        );

        panel.add(addressLabel);


        addressField =
                new JTextField();

        addressField.setBounds(
                180,
                160,
                350,
                25
        );

        panel.add(addressField);


        // PHONE

        JLabel phoneLabel =
                new JLabel("Phone:");

        phoneLabel.setBounds(
                60,
                200,
                100,
                25
        );

        panel.add(phoneLabel);


        phoneField =
                new JTextField();

        phoneField.setBounds(
                180,
                200,
                350,
                25
        );

        panel.add(phoneField);


        // PROFILE PICTURE

        JLabel pictureLabel =
                new JLabel("Profile Picture:");

        pictureLabel.setBounds(
                60,
                240,
                110,
                25
        );

        panel.add(pictureLabel);


        pictureField =
                new JTextField();

        pictureField.setBounds(
                180,
                240,
                250,
                25
        );

        panel.add(pictureField);


        JButton browseButton =
                new JButton("Browse");

        browseButton.setBounds(
                440,
                240,
                90,
                25
        );

        panel.add(browseButton);


        // SKILLS

        JLabel skillsLabel =
                new JLabel("Skills:");

        skillsLabel.setBounds(
                60,
                290,
                100,
                25
        );

        panel.add(skillsLabel);


        skillsArea =
                new JTextArea();

        skillsArea.setLineWrap(true);

        skillsArea.setWrapStyleWord(true);


        JScrollPane skillsScroll =
                new JScrollPane(
                        skillsArea
                );

        skillsScroll.setBounds(
                180,
                290,
                350,
                120
        );

        panel.add(skillsScroll);


        // SAVE BUTTON

        JButton saveButton =
                new JButton("Save Profile");

        saveButton.setBounds(
                210,
                450,
                150,
                40
        );

        panel.add(saveButton);


        // BROWSE BUTTON

        browseButton.addActionListener(
                e -> choosePicture()
        );


        // SAVE BUTTON

        saveButton.addActionListener(
                e -> saveProfile()
        );


        add(panel);
    }


    // =========================
    // LOAD PROFILE
    // =========================

    private void loadProfile() {

        WorkerProfile profile =
                profileDAO.getProfile(
                        jobSeeker.getUserId()
                );


        if (profile != null) {

            addressField.setText(
                    profile.getAddress()
            );

            phoneField.setText(
                    profile.getPhone()
            );

            pictureField.setText(
                    profile.getProfilePicture()
            );

            skillsArea.setText(
                    profile.getSkills()
            );
        }
    }


    // =========================
    // CHOOSE PROFILE PICTURE
    // =========================

    private void choosePicture() {

        JFileChooser fileChooser =
                new JFileChooser();


        int result =
                fileChooser.showOpenDialog(
                        this
                );


        if (
                result ==
                        JFileChooser.APPROVE_OPTION
        ) {

            pictureField.setText(
                    fileChooser
                            .getSelectedFile()
                            .getAbsolutePath()
            );
        }
    }


    // =========================
    // SAVE PROFILE
    // =========================

    private void saveProfile() {

        String address =
                addressField
                        .getText()
                        .trim();


        String phone =
                phoneField
                        .getText()
                        .trim();


        String picture =
                pictureField
                        .getText()
                        .trim();


        String skills =
                skillsArea
                        .getText()
                        .trim();


        if (
                address.isEmpty() ||
                        phone.isEmpty() ||
                        skills.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill address, phone and skills."
            );

            return;
        }


        WorkerProfile profile =
                profileDAO.getProfile(
                        jobSeeker.getUserId()
                );


        boolean success;


        if (profile == null) {

            profile =
                    new WorkerProfile(
                            jobSeeker.getUserId(),
                            address,
                            phone,
                            picture,
                            skills
                    );

            success =
                    profileDAO.createProfile(
                            profile
                    );

        } else {

            profile.setAddress(address);

            profile.setPhone(phone);

            profile.setProfilePicture(
                    picture
            );

            profile.setSkills(skills);

            success =
                    profileDAO.updateProfile(
                            profile
                    );
        }


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Profile saved successfully!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}