import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class Person {
    protected String nume;
    protected String dataNasterii;

    public Person(String nume, String dataNasterii) {
        this.nume = nume;
        this.dataNasterii = dataNasterii;
    }

    public String getNume() {
        return nume;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }
}

/**
 * Fiecare student are un numar de inregistrare unic
 */
class Student extends Person {
    private int numarInregistrare;

    public Student(String nume, String dataNasterii, int numarInregistrare) {
        super(nume, dataNasterii);
        this.numarInregistrare = numarInregistrare;
    }
}

/**
 * Fiecare profesor propune un proiect
 */
class Teacher extends Person {
    private List<Project> proiecte = new ArrayList<>();

    public Teacher(String nume, String dataNasterii) {
        super(nume, dataNasterii);
    }

    public void propuneProiect(Project proiect) {
        if (!proiecte.contains(proiect)) {
            proiecte.add(proiect);
        }
    }
}

/**
 * Proiecte.
 */
class Project {
    private String titlu;
    private Teacher propunator;

    public Project(String titlu, Teacher propunator) {
        this.titlu = titlu;
        this.propunator = propunator;
    }

    public String getTitlu() {
        return titlu;
    }

    public Teacher getPropunator() {
        return propunator;
    }
}

class Problem {
    private List<Student> studenti = new ArrayList<>();
    private List<Teacher> profesori = new ArrayList<>();
    private List<Project> proiecte = new ArrayList<>();

    public void adaugaStudent(Student student) { if (!studenti.contains(student)) studenti.add(student); }
    public void adaugaProfesor(Teacher profesor) { if (!profesori.contains(profesor)) profesori.add(profesor); }
    public void adaugaProiect(Project proiect) { if (!proiecte.contains(proiect)) proiecte.add(proiect); }
    public List<Student> getStudenti() { return studenti; }
    public List<Project> getProiecte() { return proiecte; }
}

/**
 * Implementarea  algoritmului greedy pentru alocarea proiectelor
 */
class ProjectAllocator {
    public static void aloca(Problem problema) {
        List<Student> studenti = problema.getStudenti();
        List<Project> proiecte = problema.getProiecte();
        for (int i = 0; i < studenti.size(); i++) {
            System.out.println(studenti.get(i).getNume() + " a primit proiectul " + (i < proiecte.size() ? proiecte.get(i).getTitlu() : "fără proiect"));
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Problem problema = new Problem();

        Teacher profesor1 = new Teacher("Andrei Ionut ", "1978-06-15");
        Teacher profesor2 = new Teacher("Vlad Alex", "1982-09-23");
        Project[] proiecte = {new Project("AI", profesor1), new Project("Algoritmi Genetici", profesor1), new Project("Web Design", profesor2)};
        Student[] studenti = {new Student("Andrei Popu", "2002-04-10", 101), new Student("Marian Radu", "2004-07-15", 102), new Student("Ioan Dumitru", "2002-12-05", 103)};

        for (Project p : proiecte) { p.getPropunator().propuneProiect(p); problema.adaugaProiect(p); }
        for (Teacher t : new Teacher[]{profesor1, profesor2}) problema.adaugaProfesor(t);
        for (Student s : studenti) problema.adaugaStudent(s);

        ProjectAllocator.aloca(problema);
    }
}
