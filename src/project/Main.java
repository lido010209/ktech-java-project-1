package project;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<ToDoList> list = bufferedReader();

        while (true) {
            //Welcome
            System.out.println();
            System.out.println("Welcome!");
            System.out.println();
            //Number of to dos
            if (list.size() == 0) {
                System.out.println("You have no more TODOs left!!!");
            } else if (list.size() == 1) {
                System.out.println("You have 1 TODO left!!!");
                System.out.println();
                System.out.println("1. "+list.get(0).getTitle());
            } else {
                int count = 0;
                for (ToDoList task : list) {
                    if (!task.getTitle().contains("(Done)")) {
                        count++;
                    }
                }
                System.out.println(String.format(
                        "You have %d TODOs left!!!", count));
                System.out.println();
                list = list.stream().sorted((d1, d2) -> {
                    LocalDate date1 = LocalDate.parse(d1.getDeadline());
                    LocalDate date2 = LocalDate.parse(d2.getDeadline());
                    return date1.compareTo(date2);
                }).collect(Collectors.toList());

                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i).getTitle());
                }
            }
            System.out.println();

            System.out.println("1. Create TODO");
            System.out.println("2. Edit TODO");
            System.out.println("3. Finish TODO");
            System.out.println("4. Delete TODO");
            System.out.println("5. Exit");
            System.out.println();

            System.out.print("Input: ");
            String inputString = reader.readLine();
            ToDoInputNumber inputNumber = new ToDoInputNumber(inputString, 5);
            int input = inputNumber.getNumber();

            //1. Create TODO
            if (input == 1) {
                System.out.print("Title: ");
                String title = reader.readLine();

                System.out.print("Until: ");
                String deadline = reader.readLine();
                try {
                    list.add(new ToDoList(title, LocalDate.parse(deadline).toString()));
                } catch (Exception e) {
                    System.out.println("Please check valid format of date!!!");
                }

            }

            //2. Edit TODO
            else if (input == 2) {
                System.out.print("Edit TODO number: ");
                String editString = reader.readLine();
                ToDoInputNumber numToDo = new ToDoInputNumber(editString, list.size());
                int editNumber = numToDo.getNumber();
                if (editNumber > 0 && editNumber <= list.size()) {
                    System.out.print("Title: ");
                    String title = reader.readLine();

                    if (!title.isEmpty()) {
                        ToDoList taskNew = new ToDoList(title, list.get(editNumber - 1).getDeadline());
                        list.set(editNumber - 1, taskNew);
                    }
                    else {
                        System.out.print("Until: ");
                        String deadline = reader.readLine();

                        try {
                            ToDoList taskNew = new ToDoList(list.get(editNumber-1).getTitle(),
                                    LocalDate.parse(deadline).toString());
                            list.set(editNumber - 1, taskNew);
                            System.out.println("Saved!!!");
                        } catch (Exception e) {
                            System.out.println("Please check valid format of date!!!");
                        }
                    }
                } else continue;
            }

            //3. Finish TODO
            else if (input == 3) {
                System.out.print("Finish TODO number: ");
                String finishString = reader.readLine();
                ToDoInputNumber numToDo = new ToDoInputNumber(finishString, list.size());
                int finishNumber = numToDo.getNumber();

                if (finishNumber > 0 && finishNumber <= list.size()) {
                    if (list.get(finishNumber-1).getTitle().contains(" (Done)")) continue;
                    list.get(finishNumber - 1).setTitle(
                            list.get(finishNumber - 1).getTitle() + " (Done)");
                } else continue;
            }

            //4. Delete TODO
            else if (input == 4) {
                System.out.print("Delete TODO number: ");
                String deleteString = reader.readLine();
                ToDoInputNumber numToDo = new ToDoInputNumber(deleteString, list.size());
                int deleteNumber = numToDo.getNumber();

                if (deleteNumber > 0 && deleteNumber <= list.size()) {
                    list.remove(deleteNumber - 1);
                } else continue;

            }
            else if (input == 5) {
                break;
            }
        }
        bufferedWriter(list);
    }

    public static void bufferedWriter(List<ToDoList> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/project/project.csv"))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (ToDoList l : list) {
                StringBuilder s = new StringBuilder();
                s.append(l.getTitle()).append(",")
                        .append(l.getDeadline());
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<ToDoList> bufferedReader() {
        List<ToDoList> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/project/project.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] ele = line.split(",");
                list.add(new ToDoList(ele[0], ele[1]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
