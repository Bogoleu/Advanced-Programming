//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

abstract class Aircraft implements Comparable<Aircraft>
{
    protected string name;
    public Aircraft(string Name){
        this.name=name;
    }
    public string getName(){
        return name;
    }

    @Override
    public int compareTo(Aircraft other) {
        return this.name.compareTo(other.name);
    }
    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
