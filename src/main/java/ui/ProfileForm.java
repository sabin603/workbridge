package ui;

import dao.ProfileDAO;
import model.User;
import model.WorkerProfile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProfileForm extends JFrame {

    private User jobSeeker;
    private ProfileDAO profileDAO;

    private JTextField addressField;
    private JTextField phoneField;
    private JTextField pictureField;
    private JTextField cvField;
    private JTextField headlineField;
    private JTextField educationField;

    private JTextArea skillsArea;
    private JTextArea experienceArea;

    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel picturePreview;

    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(31, 41, 55);
    private final Color MUTED = new Color(107, 114, 128);

    public ProfileForm(User user) {

        this.jobSeeker = user;
        profileDAO = new ProfileDAO();

        setTitle("WorkBridge - My Profile");
        setSize(750, 780);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadProfile();
    }

    private void createUI() {

        getContentPane().setBackground(BACKGROUND);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(WHITE);
        headerPanel.setBorder(
                new EmptyBorder(18, 20, 18, 20)
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );
        titlePanel.setBackground(WHITE);

        JLabel titleLabel =
                new JLabel("My Profile");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        titleLabel.setForeground(TEXT);

        JLabel subtitleLabel =
                new JLabel(
                        "Build a strong professional profile"
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setForeground(MUTED);

        titlePanel.add(titleLabel);
        titlePanel.add(
                Box.createVerticalStrut(5)
        );
        titlePanel.add(subtitleLabel);

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(WHITE);
        formPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7,
                        7,
                        7,
                        7
                );

        gbc.fill = GridBagConstraints.HORIZONTAL;


        // =========================
        // PROFILE PICTURE
        // =========================

        picturePreview =
                new JLabel();

        picturePreview.setPreferredSize(
                new Dimension(100, 100)
        );

        picturePreview.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        picturePreview.setVerticalAlignment(
                SwingConstants.CENTER
        );

        picturePreview.setBorder(
                BorderFactory.createLineBorder(
                        new Color(209, 213, 219)
                )
        );

        picturePreview.setText("Photo");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.weightx = 0;

        formPanel.add(
                picturePreview,
                gbc
        );


        JPanel identityPanel =
                new JPanel();

        identityPanel.setLayout(
                new BoxLayout(
                        identityPanel,
                        BoxLayout.Y_AXIS
                )
        );

        identityPanel.setBackground(WHITE);

        nameLabel =
                new JLabel(
                        jobSeeker.getName()
                );

        nameLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        nameLabel.setForeground(TEXT);

        emailLabel =
                new JLabel(
                        jobSeeker.getEmail()
                );

        emailLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        emailLabel.setForeground(MUTED);

        identityPanel.add(nameLabel);
        identityPanel.add(
                Box.createVerticalStrut(5)
        );
        identityPanel.add(emailLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 1;

        formPanel.add(
                identityPanel,
                gbc
        );


        JButton browsePictureButton =
                new JButton("Choose Picture");

        styleButton(
                browsePictureButton,
                PRIMARY
        );

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1;

        formPanel.add(
                browsePictureButton,
                gbc
        );


        // =========================
        // HEADLINE
        // =========================

        gbc.gridheight = 1;

        addLabel(
                formPanel,
                gbc,
                "Professional Headline",
                0,
                4
        );

        headlineField =
                new JTextField();

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        formPanel.add(
                headlineField,
                gbc
        );


        // =========================
        // ADDRESS
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Address",
                0,
                5
        );

        addressField =
                new JTextField();

        addField(
                formPanel,
                gbc,
                addressField,
                5
        );


        // =========================
        // PHONE
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Phone",
                0,
                6
        );

        phoneField =
                new JTextField();

        addField(
                formPanel,
                gbc,
                phoneField,
                6
        );


        // =========================
        // EDUCATION
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Education",
                0,
                7
        );

        educationField =
                new JTextField();

        addField(
                formPanel,
                gbc,
                educationField,
                7
        );


        // =========================
        // SKILLS
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Skills",
                0,
                8
        );

        skillsArea =
                new JTextArea(3, 20);

        skillsArea.setLineWrap(true);
        skillsArea.setWrapStyleWord(true);

        JScrollPane skillsScroll =
                new JScrollPane(
                        skillsArea
                );

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0;

        formPanel.add(
                skillsScroll,
                gbc
        );


        // =========================
        // EXPERIENCE
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Experience",
                0,
                9
        );

        experienceArea =
                new JTextArea(4, 20);

        experienceArea.setLineWrap(true);
        experienceArea.setWrapStyleWord(true);

        JScrollPane experienceScroll =
                new JScrollPane(
                        experienceArea
                );

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        formPanel.add(
                experienceScroll,
                gbc
        );


        // =========================
        // CV
        // =========================

        addLabel(
                formPanel,
                gbc,
                "CV / Resume",
                0,
                10
        );

        cvField =
                new JTextField();

        cvField.setEditable(false);

        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.weightx = 1;

        formPanel.add(
                cvField,
                gbc
        );


        JButton browseCVButton =
                new JButton("Choose CV");

        styleButton(
                browseCVButton,
                PRIMARY
        );

        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.gridwidth = 1;

        formPanel.add(
                browseCVButton,
                gbc
        );


        // =========================
        // SAVE
        // =========================

        JButton saveButton =
                new JButton("Save Profile");

        styleButton(
                saveButton,
                PRIMARY
        );

        saveButton.setPreferredSize(
                new Dimension(
                        160,
                        42
                )
        );

        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        formPanel.add(
                saveButton,
                gbc
        );


        JScrollPane formScroll =
                new JScrollPane(
                        formPanel
                );

        formScroll.setBorder(null);
        formScroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        mainPanel.add(
                formScroll,
                BorderLayout.CENTER
        );

        add(mainPanel);


        // =========================
        // ACTIONS
        // =========================

        browsePictureButton.addActionListener(
                e -> choosePicture()
        );

        browseCVButton.addActionListener(
                e -> chooseCV()
        );

        saveButton.addActionListener(
                e -> saveProfile()
        );
    }


    // =========================
    // ADD LABEL
    // =========================

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text,
            int x,
            int y
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(TEXT);

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0;

        panel.add(
                label,
                gbc
        );
    }


    // =========================
    // ADD FIELD
    // =========================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            JTextField field,
            int y
    ) {

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        panel.add(
                field,
                gbc
        );
    }


    // =========================
    // BUTTON STYLE
    // =========================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );
    }


    // =========================
    // LOAD PROFILE
    // =========================

    private void loadProfile() {

        WorkerProfile profile =
                profileDAO.getProfile(
                        jobSeeker.getUserId()
                );

        if (profile == null) {
            return;
        }

        addressField.setText(
                safe(profile.getAddress())
        );

        phoneField.setText(
                safe(profile.getPhone())
        );

        pictureFieldSet(
                safe(profile.getProfilePicture())
        );

        skillsArea.setText(
                safe(profile.getSkills())
        );

        headlineField.setText(
                safe(profile.getHeadline())
        );

        educationField.setText(
                safe(profile.getEducation())
        );

        experienceArea.setText(
                safe(profile.getExperience())
        );

        cvField.setText(
                safe(profile.getCvPath())
        );

        loadPicturePreview(
                profile.getProfilePicture()
        );
    }


    // =========================
    // SAFE STRING
    // =========================

    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }


    // =========================
    // PICTURE FIELD
    // =========================

    private void pictureFieldSet(
            String value
    ) {

        pictureField =
                new JTextField();

        pictureField.setText(value);
    }


    // =========================
    // CHOOSE PICTURE
    // =========================

    private void choosePicture() {

        JFileChooser chooser =
                new JFileChooser();

        chooser.setDialogTitle(
                "Select Profile Picture"
        );

        chooser.setFileFilter(
                new FileNameExtensionFilter(
                        "Image Files",
                        "jpg",
                        "jpeg",
                        "png"
                )
        );

        int result =
                chooser.showOpenDialog(this);

        if (result ==
                JFileChooser.APPROVE_OPTION) {

            File file =
                    chooser.getSelectedFile();

            String name =
                    file.getName()
                            .toLowerCase();

            if (!name.endsWith(".jpg")
                    && !name.endsWith(".jpeg")
                    && !name.endsWith(".png")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a JPG, JPEG or PNG image.",
                        "Invalid Image",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            ensurePictureField();

            pictureField.setText(
                    file.getAbsolutePath()
            );

            loadPicturePreview(
                    file.getAbsolutePath()
            );
        }
    }


    // =========================
    // ENSURE PICTURE FIELD
    // =========================

    private void ensurePictureField() {

        if (pictureField == null) {

            pictureField =
                    new JTextField();
        }
    }


    // =========================
    // LOAD PICTURE PREVIEW
    // =========================

    private void loadPicturePreview(
            String path
    ) {

        if (path == null
                || path.trim().isEmpty()) {

            picturePreview.setIcon(null);
            picturePreview.setText("Photo");

            return;
        }

        File file =
                new File(path);

        if (!file.exists()) {

            picturePreview.setIcon(null);
            picturePreview.setText("Photo");

            return;
        }

        ImageIcon original =
                new ImageIcon(path);

        Image image =
                original.getImage()
                        .getScaledInstance(
                                100,
                                100,
                                Image.SCALE_SMOOTH
                        );

        picturePreview.setText("");

        picturePreview.setIcon(
                new ImageIcon(image)
        );
    }


    // =========================
    // CHOOSE CV
    // =========================

    private void chooseCV() {

        JFileChooser chooser =
                new JFileChooser();

        chooser.setDialogTitle(
                "Select CV / Resume"
        );

        chooser.setFileFilter(
                new FileNameExtensionFilter(
                        "PDF Files",
                        "pdf"
                )
        );

        int result =
                chooser.showOpenDialog(this);

        if (result ==
                JFileChooser.APPROVE_OPTION) {

            File file =
                    chooser.getSelectedFile();

            String name =
                    file.getName()
                            .toLowerCase();

            if (!name.endsWith(".pdf")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a PDF file.",
                        "Invalid CV",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            cvField.setText(
                    file.getAbsolutePath()
            );
        }
    }


    // =========================
    // SAVE PROFILE
    // =========================

    private void saveProfile() {

        ensurePictureField();

        String address =
                addressField.getText()
                        .trim();

        String phone =
                phoneField.getText()
                        .trim();

        String picture =
                pictureField.getText()
                        .trim();

        String headline =
                headlineField.getText()
                        .trim();

        String education =
                educationField.getText()
                        .trim();

        String skills =
                skillsArea.getText()
                        .trim();

        String experience =
                experienceArea.getText()
                        .trim();

        String cv =
                cvField.getText()
                        .trim();


        // =========================
        // VALIDATION
        // =========================

        if (address.isEmpty()
                || phone.isEmpty()
                || skills.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill address, phone and skills.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        WorkerProfile profile =
                profileDAO.getProfile(
                        jobSeeker.getUserId()
                );

        boolean success;


        // =========================
        // CREATE
        // =========================

        if (profile == null) {

            profile =
                    new WorkerProfile(
                            jobSeeker.getUserId(),
                            address,
                            phone,
                            picture,
                            skills
                    );

            profile.setHeadline(
                    headline
            );

            profile.setEducation(
                    education
            );

            profile.setExperience(
                    experience
            );

            profile.setCvPath(
                    cv
            );

            success =
                    profileDAO.createProfile(
                            profile
                    );
        }


        // =========================
        // UPDATE
        // =========================

        else {

            profile.setAddress(
                    address
            );

            profile.setPhone(
                    phone
            );

            profile.setProfilePicture(
                    picture
            );

            profile.setHeadline(
                    headline
            );

            profile.setEducation(
                    education
            );

            profile.setExperience(
                    experience
            );

            profile.setSkills(
                    skills
            );

            profile.setCvPath(
                    cv
            );

            success =
                    profileDAO.updateProfile(
                            profile
                    );
        }


        // =========================
        // RESULT
        // =========================

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Profile updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadProfile();

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