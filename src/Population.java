import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Population {

    private List<Individual> individuals;
    private int size = 0;

    /*Constructeur de notre population :  si on passe true au booléen il
    * rempli notre population d'individus*/
    public Population(boolean createNew) {
        Random random = new Random();
        this.size = random.nextInt(50);
        individuals = new ArrayList<>();
        if (createNew) {
            createNewPopulation(size);
        }
    }

    public Population(int pop_size, boolean createNew) {
        Random random = new Random();
        this.size = random.nextInt(100);
        individuals = new ArrayList<>();
        if (createNew) {
            createNewPopulation(size);
        }
    }

    protected Individual getIndividual(int index) {
        return individuals.get(index);
    }

    //TODO ************** Choisir l'individu le plus apte *********************
    protected Individual getFittest() {
        Individual fittest = individuals.get(0);
        for (int i = 0; i < individuals.size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    //*************************** CREER UNE NOUVELLE POPULATION ******************************************
    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Individual newIndividual = new Individual();
            individuals.add(i, newIndividual);
        }
    }

    //****************************** AFFICHER LA POPULATION ***********************************************

    public void affichePopulation(){
        String phrase = "";

        for (int i = 0; i < individuals.size(); i++){

            phrase = " - Individu " + (i+1) + " : \n    *Nombre de Gènes (" + individuals.get(i).getDefaultGeneLength() + " )" +
                    " :  " + Arrays.toString(individuals.get(i).getGenes()) + " \n    *Fitness Score : " + individuals.get(i).getFitness();
            System.out.println(phrase);
        }
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }

    //***************************** RENVOIE LA TAILLE DE LA POPULATION **********************************************************
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}