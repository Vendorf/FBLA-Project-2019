package view;

import dao.GenderDAO;
import dao.GradeDAO;
import entities.Gender;
import entities.Grade;
import entities.Student;
import event.EventManager;
import event.EventType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class StudentTableView extends TableView<Student> {

    //No longer needed (right?)
    // OP A
    //factory does not directly apply to the column
    //instead, it is used in order to populate the true factories which are Student, Textbox



    //private TableColumn<Student, Integer> idColumn;
    private TableColumn<Student, Integer> studentIdColumn;
    private TableColumn nameColumn;
    private TableColumn<Student, String> firstNameColumn;
    private TableColumn<Student, String> lastNameColumn;
    private TableColumn<Student, Date> dobColumn;

    private TableColumn<Student, Gender> genderColumn;
    private TableColumn<Student, Grade> gradeColumn;

    private Set<Student> editedStudents = new HashSet<>();


    private Student currEdit = null;

    //private TableColumn<Student, S>

    public StudentTableView(){

        //CONSIDER:
        //http://fxexperience.com
        //Use a TreeTableView and span the cells all the way across; then use the cell factory API in order to insert whatever component into the spanned cell
        //This should allow it so that when a row is clicked, the tree opens, and in that tree is the single cell with the student info

        //Check out https://www.youtube.com/watch?v=udc2iRZBF0M as well
        //You downloaded the cell spanning and it is in the project folder if you look

        this.getStyleClass().add("student-table-view");

        //idColumn = new TableColumn<>("ID");
        //idColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));

        studentIdColumn = new TableColumn<>("Student ID");
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("student_id"));
        nameColumn = new TableColumn("Name");
        firstNameColumn = new TableColumn<>("First");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("first_name"));
        lastNameColumn = new TableColumn<>("Last");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("last_name"));
        dobColumn = new TableColumn<>("DOB");
        //dobColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("dob"));
        dobColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Date>, ObservableValue<Date>>() {
            @Override
            public ObservableValue<Date> call(TableColumn.CellDataFeatures<Student, Date> studentDateCellDataFeatures) {
                return new SimpleObjectProperty(studentDateCellDataFeatures.getValue().getDob());
            }
        });

        genderColumn = new TableColumn<>("Gender");
//        genderColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> studentStringCellDataFeatures) {
//                return new SimpleStringProperty(studentStringCellDataFeatures.getValue().getGender().getGender());
//            }
//        });

        genderColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Gender>, ObservableValue<Gender>>() {
            @Override
            public ObservableValue<Gender> call(TableColumn.CellDataFeatures<Student, Gender> studentGenderCellDataFeatures) {
                return new SimpleObjectProperty(studentGenderCellDataFeatures.getValue().getGender().getGender()); //lol why the fuck does this actually work
            }
        });

        gradeColumn = new TableColumn<>("Year");
        gradeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Grade>, ObservableValue<Grade>>() {
            @Override
            public ObservableValue<Grade> call(TableColumn.CellDataFeatures<Student, Grade> studentGradeCellDataFeatures) {
                return new SimpleObjectProperty(studentGradeCellDataFeatures.getValue().getGrade().getGrade());
            }
        });

//        gradeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> studentStringCellDataFeatures) {
//                return new SimpleStringProperty(studentStringCellDataFeatures.getValue().getGrade().getGrade());
//            }
//        });



        //idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return integer.toString();
            }

            @Override
            public Integer fromString(String s) {
                int val = 0;
                try{
                    val = Integer.parseInt(s);
                }catch (NumberFormatException e){
                    //LOG here
                    System.out.println("Cancelling edit");
                    //cancel edit
                    if(currEdit != null){
                        val = currEdit.getStudent_id();
                        currEdit = null;
                    }
                }
                return val;
            }
        }));
        dobColumn.setCellFactory(new Callback<TableColumn<Student, Date>, TableCell<Student, Date>>() {
            @Override
            public TableCell<Student, Date> call(TableColumn<Student, Date> studentDateTableColumn) {
                return new TextFieldTableCell<>(){ //this is a TextFiedTableCell to provide default label functionality not present with just TableCell
                    private DatePicker datePicker;
                    private final String pattern = "yyyy-MM-dd";
                    private final DateTimeFormatter dateFormatter =
                            DateTimeFormatter.ofPattern(pattern);

                    @Override
                    public void startEdit() {
                        super.startEdit();
                        datePicker = new DatePicker();
                        datePicker.setConverter(new StringConverter<LocalDate>() {
                            @Override
                            public String toString(LocalDate date) {
                                if (date != null) {
                                    return dateFormatter.format(date);
                                } else {
                                    return "";
                                }
                            }
                            @Override
                            public LocalDate fromString(String string) {
                                if (string != null && !string.isEmpty()) {
                                    return LocalDate.parse(string, dateFormatter);
                                } else {
                                    return null;
                                }
                            }
                        });
                        datePicker.setPromptText(pattern.toLowerCase());
                        datePicker.setValue(LocalDate.parse(currEdit.getDob().toString(), dateFormatter));
                        datePicker.prefWidthProperty().bind(studentDateTableColumn.widthProperty());
                        setGraphic(datePicker);

//                        datePicker.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//                            @Override
//                            public void handle(KeyEvent keyEvent) {
//                                if(keyEvent.getCode() == KeyCode.ENTER){
//                                    cancelEdit();
//                                }
//                            }
//                        });
                    }
                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        if(currEdit != null && datePicker.getValue() != null){
                            //Date newDate = Date.valueOf(datePicker.getValue());
                            try{
                                Date newDate = Date.valueOf(datePicker.getEditor().getText());
                                if(!newDate.equals(currEdit.getDob())){
                                    currEdit.setDob(newDate);
                                    editedStudents.add(currEdit);
                                    studentDateTableColumn.setVisible(false); //force refresh of column
                                    studentDateTableColumn.setVisible(true);
                                }
                            }catch (IllegalArgumentException e){
                                //LOG
                                System.out.println("Invalid date");
                            }
                        }
                        currEdit = null;
                    }
                };
            }
        });

//        try {
//            genderColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(new StringConverter<>() {
//                @Override
//                public String toString(Gender gender) {
//                    return gender.getGender();
//                }
//
//                @Override
//                public Gender fromString(String s) {
//                    return null;
//                }
//            }, GenderDAO.searchGenders()));
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }


                //this is a massive hack rn; it works but is not directly editing the ObservableList which may cause problems
                //may just have to return to TableColumn<Student, String> and mess with that as the direct Gender thing is having a lot of problems
                //while seemingly alright at the moment, could be problematic in the future

                //it is a secondary concern though; it is working for now so work on developing vital features first before fixing this

                //solve it later, whatever
                genderColumn.setCellFactory(new Callback<TableColumn<Student, Gender>, TableCell<Student, Gender>>() {
                    @Override
                    public TableCell<Student, Gender> call(TableColumn<Student, Gender> studentGenderTableColumn) {
                        return new ChoiceBoxTableCell<>() {
                            private ChoiceBox<Gender> choiceBox;

                            @Override
                            public void startEdit() {
                                super.startEdit();
                                choiceBox = new ChoiceBox<>();
                                choiceBox.setConverter(new StringConverter<Gender>() {
                                    @Override
                                    public String toString(Gender gender) {
                                        return gender.getGender();
                                    }

                                    @Override
                                    public Gender fromString(String s) {
                                        return null;
                                    }
                                });
                                try {
                                    choiceBox.setItems(GenderDAO.searchGenders());
                                } catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                choiceBox.prefWidthProperty().bind(studentGenderTableColumn.widthProperty());
                                setGraphic(choiceBox);

                                choiceBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
                                    @Override
                                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                        if (!t1) {
                                            if (currEdit != null && choiceBox.getValue() != null) {
                                                System.out.println("Committing");
                                                //currEdit.setGender(choiceBox.getSelectionModel().getSelectedItem());
                                                currEdit.setGender(choiceBox.getValue());
                                                studentGenderTableColumn.setVisible(false); //this is done in order to force the column to be redrawn
                                                studentGenderTableColumn.setVisible(true);
                                                editedStudents.add(currEdit);
                                                currEdit = null;
                                            }
                                        }
                                    }
                                });
                            }
                        };
                    }
                });

                gradeColumn.setCellFactory(new Callback<TableColumn<Student, Grade>, TableCell<Student, Grade>>() {
                    @Override
                    public TableCell<Student, Grade> call(TableColumn<Student, Grade> studentGradeTableColumn) {
                        return new ChoiceBoxTableCell<>() {
                            private ChoiceBox<Grade> choiceBox;
                            @Override
                            public void startEdit() {
                                super.startEdit();
                                choiceBox = new ChoiceBox<>();
                                choiceBox.setConverter(new StringConverter<Grade>() {
                                    @Override
                                    public String toString(Grade grade) {
                                        return grade.getGrade();
                                    }

                                    @Override
                                    public Grade fromString(String s) {
                                        return null;
                                    }
                                });
                                try {
                                    choiceBox.setItems(GradeDAO.searchGrades());
                                } catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                choiceBox.prefWidthProperty().bind(studentGradeTableColumn.widthProperty());
                                setGraphic(choiceBox);

                                choiceBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
                                    @Override
                                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                        if (!t1) {
                                            if (currEdit != null && choiceBox.getValue() != null) {
                                                System.out.println("Committing");
                                                //currEdit.setGender(choiceBox.getSelectionModel().getSelectedItem());
                                                currEdit.setGrade(choiceBox.getValue());
                                                studentGradeTableColumn.setVisible(false); //this is done in order to force the column to be redrawn
                                                studentGradeTableColumn.setVisible(true);
                                                editedStudents.add(currEdit);
                                                currEdit = null;
                                            }
                                        }
                                    }
                                });
                            }
                        };
                    }
                });

//        genderColumn.setCellFactory(new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
//            @Override
//            public TableCell<Student, String> call(TableColumn<Student, String> studentStringTableColumn) {
//                return new ChoiceBoxTableCell<>(){
//                    @Override
//                    public void startEdit() {
//                        super.startEdit();
//                        ChoiceBox<Gender> choiceBox = new ChoiceBox<>();
//                        choiceBox.setConverter(new StringConverter<Gender>() {
//                            @Override
//                            public String toString(Gender gender) {
//                                return gender.getGender();
//                            }
//
//                            @Override
//                            public Gender fromString(String s) {
//                                return null;
//                            }
//                        });
//                        try {
//                            choiceBox.setItems(GenderDAO.searchGenders());
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        setGraphic(choiceBox);
//                    }
//                };
//            }
//        });


        firstNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
                if(studentStringCellEditEvent.getNewValue() != studentStringCellEditEvent.getOldValue()){
                    studentStringCellEditEvent.getTableView().getItems().get(studentStringCellEditEvent.getTablePosition().getRow()).setFirst_name(studentStringCellEditEvent.getNewValue());
                    editedStudents.add(studentStringCellEditEvent.getRowValue());
                    System.out.println(studentStringCellEditEvent.getRowValue().toString()); //can set edited flag here or something
                }
            }
        });

        lastNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
                if(studentStringCellEditEvent.getNewValue() != studentStringCellEditEvent.getOldValue()) {
                    studentStringCellEditEvent.getTableView().getItems().get(studentStringCellEditEvent.getTablePosition().getRow()).setLast_name(studentStringCellEditEvent.getNewValue());
                    editedStudents.add(studentStringCellEditEvent.getRowValue());
                    System.out.println(studentStringCellEditEvent.getRowValue().toString()); //can set edited flag here or something
                }
            }
        });

        studentIdColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, Integer> studentIntegerCellEditEvent) {
                currEdit = studentIntegerCellEditEvent.getRowValue();
            }
        });

        studentIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, Integer> studentIntegerCellEditEvent) {
                if(studentIntegerCellEditEvent.getNewValue() != studentIntegerCellEditEvent.getOldValue()){
                    studentIntegerCellEditEvent.getTableView().getItems().get(studentIntegerCellEditEvent.getTablePosition().getRow()).setStudent_id(studentIntegerCellEditEvent.getNewValue());
                    editedStudents.add(studentIntegerCellEditEvent.getRowValue());
                    System.out.println(studentIntegerCellEditEvent.getRowValue().toString()); //can set edited flag here or something
                }
            }
        });

        dobColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Student, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, Date> studentDateCellEditEvent) {
                currEdit = studentDateCellEditEvent.getRowValue();
            }
        });

        genderColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Student, Gender>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, Gender> studentGenderCellEditEvent) {
                currEdit = studentGenderCellEditEvent.getRowValue();
            }
        });

        gradeColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Student, Grade>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, Grade> studentGradeCellEditEvent) {
                currEdit = studentGradeCellEditEvent.getRowValue();
            }
        });

//        genderColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Gender>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Student, Gender> studentGenderCellEditEvent) {
//                if(studentGenderCellEditEvent.getNewValue() != studentGenderCellEditEvent.getOldValue()){
//                    studentGenderCellEditEvent.getTableView().getItems().get(studentGenderCellEditEvent.getTablePosition().getRow()).setGender(studentGenderCellEditEvent.getNewValue());
//                }
//            }
//        });

//        genderColumn.setOnEditCancel(new EventHandler<TableColumn.CellEditEvent<Student, Gender>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Student, Gender> studentGenderCellEditEvent) {
//                System.out.println(studentGenderCellEditEvent == null);
//                System.out.println(studentGenderCellEditEvent.getTableView() == null);
//                if(newValue != null){
//                    studentGenderCellEditEvent.getTableView().getItems().get(studentGenderCellEditEvent.getTablePosition().getRow()).setGender(newValue);
//                    newValue = null;
//                }
//                //studentGenderCellEditEvent.getTableView().getItems().get(studentGenderCellEditEvent.getTablePosition().getRow()).setGender(studentGenderCellEditEvent.getNewValue());
//            }
//        });

//        genderColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
//                if(studentStringCellEditEvent.getNewValue() != studentStringCellEditEvent.getOldValue()){
//                    //studentStringCellEditEvent.getTableView().getItems().get(studentStringCellEditEvent.getTablePosition().getRow()).setGender();
//                }
//            }
//        });

//straight garbage class; needs rewrite
//        dobColumn.setCellFactory(new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
//            @Override
//            public TableCell<Student, String> call(TableColumn<Student, String> studentStringTableColumn) {
//                DatePickerCell datePick = new DatePickerCell();
//                return datePick;
//            }
//        });

        this.setEditable(true);

        /*
        * .setCellValueFactory(new Callback<CellDataFeatures<Enseignant,String>,ObservableValue<String>>(){

                @Override
                public ObservableValue<String> call(CellDataFeatures<Enseignant, String> param) {
                    return new SimpleStringProperty(param.getValue().getMatiere().getIntitule());
                }
            });
        * */

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.getColumns().setAll(firstNameColumn, lastNameColumn);
        this.getColumns().setAll(/*idColumn, */studentIdColumn, nameColumn, dobColumn, genderColumn, gradeColumn);

        this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observableValue, Student oldStudent, Student newStudent) {
                if(newStudent != null){
                    System.out.println(newStudent.toString()); //put student info into panel
                    EventManager.post(EventType.STUDENT_SELECTED);
                }
            }
        });

    }

    public void print(){
        for(Student student : this.getItems()){
            System.out.println(student.toString());
        }
        for(Student student : editedStudents){
            System.out.println(student.toString());
        }
    }

    public Set<Student> getEditedStudents() {
        return editedStudents;
    }

    public void setData(ObservableList<Student> students){
        this.setItems(students);
        editedStudents = new HashSet<>();
        this.getItems().addListener(new ListChangeListener<Student>() {
            @Override
            public void onChanged(Change<? extends Student> change) {
                System.out.println("I was changed"); //why is this here again?
            }
        });


//        this.getItems().addListener((ListChangeListener.Change<? extends Student> c) -> {
//            while (c.next()) {
//                if (c.wasUpdated()) {
//                    int start = c.getFrom() ;
//                    int end = c.getTo() ;
//                    for (int i = start ; i < end ; i++) {
//                        System.out.println("Element at position "+i+" was updated to: " +c.getList().get(i).toString());
//                    }
//                }
//            }
//        });
    }

}



/*
* //        genderColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> studentStringCellDataFeatures) {
//                return new SimpleStringProperty(studentStringCellDataFeatures.getValue().getGender().getGender());
//            }
//        });

//        genderColumn.setCellFactory(new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
//            @Override
//            public TableCell<Student, String> call(TableColumn<Student, String> studentStringTableColumn) {
//                return new ChoiceBoxTableCell<>(){
//                    @Override
//                    public void startEdit() {
//                        super.startEdit();
//                        ChoiceBox<Gender> choiceBox = new ChoiceBox<>();
//                        choiceBox.setConverter(new StringConverter<Gender>() {
//                            @Override
//                            public String toString(Gender gender) {
//                                return gender.getGender();
//                            }
//
//                            @Override
//                            public Gender fromString(String s) {
//                                return null;
//                            }
//                        });
//                        try {
//                            choiceBox.setItems(GenderDAO.searchGenders());
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        setGraphic(choiceBox);
//                    }
//                };
//            }
//        });



//        genderColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
//                if(studentStringCellEditEvent.getNewValue() != studentStringCellEditEvent.getOldValue()){
//                    //studentStringCellEditEvent.getTableView().getItems().get(studentStringCellEditEvent.getTablePosition().getRow()).setGender();
//                }
//            }
//        });

*
*
* */