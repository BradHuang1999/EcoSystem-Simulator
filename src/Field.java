import java.util.ArrayList;


/**
 * @author 348916626
 *
 */
public class Field {

    /**
     *
     */

    private Organism[][] fieldGrid;
    private int sideLength;
    private int grassHealth, sheepHealth, wolfHealth;

    public Organism[][] getFieldGrid() {
        return this.fieldGrid;
    }

    public Field(int sideLength, double grassPercent, double maleSheepPercent, double femaleSheepPercent, double maleWolfPercent, double femaleWolfPercent, int grassHealth, int sheepHealth, int wolfHealth) {
        System.out.println("Field");

        this.fieldGrid = new Organism[sideLength][sideLength];

        this.sideLength = sideLength;
        this.grassHealth = grassHealth;
        this.sheepHealth = sheepHealth;
        this.wolfHealth = wolfHealth;

        double determinant;

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                determinant = Math.random();
                if (determinant < grassPercent) {
                    this.fieldGrid[i][j] = new Grass(grassHealth);
                } else if (determinant > grassPercent && determinant < grassPercent + maleSheepPercent) {
                    this.fieldGrid[i][j] = new Sheep(sheepHealth, 0);
                } else if (determinant > grassPercent + maleSheepPercent && determinant < grassPercent + maleSheepPercent + femaleSheepPercent) {
                    this.fieldGrid[i][j] = new Sheep(sheepHealth, 1);
                } else if (determinant > grassPercent + maleSheepPercent + femaleSheepPercent && determinant < grassPercent + maleSheepPercent + femaleSheepPercent + maleWolfPercent) {
                    this.fieldGrid[i][j] = new Wolf(wolfHealth, 0);
                } else if (determinant > grassPercent + maleSheepPercent + femaleSheepPercent + maleWolfPercent && determinant < grassPercent + maleSheepPercent + femaleSheepPercent + maleWolfPercent + femaleWolfPercent) {
                    this.fieldGrid[i][j] = new Wolf(wolfHealth, 1);
                } else {
                    this.fieldGrid[i][j] = null;
                }
            }
        }
    }

    public void turn(int grassGrowRate) {

        int desX, desY;
        int[] rdmCoordinate;

        int sheepBorn = 0, wolfBorn = 0, sheepThere = 0, wolfThere = 0, sheepEaten = 0, sheepDead = 0, wolfDead = 0;

        for (int x = 0; x < this.sideLength; x++) {
            for (int y = 0; y < this.sideLength; y++) {
                if (this.fieldGrid[x][y] instanceof Sheep){
                    sheepThere++;
                } else if (this.fieldGrid[x][y] instanceof Wolf){
                    wolfThere++;
                }
            }
        }

        for (int x = 0; x < this.sideLength; x++) {
            for (int y = 0; y < this.sideLength; y++) {
                if (this.fieldGrid[x][y] != null) {
                    if (!(this.fieldGrid[x][y] instanceof Grass)) {
                        desX = x + selectMove(x);
                        desY = y + selectMove(y);
                        this.fieldGrid[x][y].addHealth(-1);

                        if (this.fieldGrid[desX][desY] == null || (this.fieldGrid[desX][desY] instanceof Grass && this.fieldGrid[x][y] instanceof Wolf)) {
                            this.fieldGrid[desX][desY] = this.fieldGrid[x][y];
                            this.fieldGrid[x][y] = null;
                        } else if (this.fieldGrid[desX][desY] instanceof Grass && this.fieldGrid[x][y] instanceof Sheep) {
                            ((Sheep) this.fieldGrid[x][y]).eatGrass((Grass) this.fieldGrid[desX][desY]);
                            this.fieldGrid[desX][desY] = this.fieldGrid[x][y];
                            this.fieldGrid[x][y] = null;
                        } else if (this.fieldGrid[desX][desY] instanceof Sheep && this.fieldGrid[x][y] instanceof Wolf) {
                            ((Wolf) this.fieldGrid[x][y]).eatSheep((Sheep) this.fieldGrid[desX][desY]);
                            this.fieldGrid[desX][desY] = this.fieldGrid[x][y];
                            sheepEaten++;
                            this.fieldGrid[x][y] = null;
                        } else if (this.fieldGrid[desX][desY] instanceof Sheep && this.fieldGrid[x][y] instanceof Sheep) {
                            if (((Sheep) this.fieldGrid[desX][desY]).getGender() != ((Sheep) this.fieldGrid[x][y]).getGender()) {
                                rdmCoordinate = this.getRandomEmpty();
                                if (rdmCoordinate[0] != -1) {
                                    //System.out.print(1);
                                    sheepBorn++;
                                    this.fieldGrid[rdmCoordinate[0]][rdmCoordinate[1]] = (new Sheep(this.sheepHealth, (int) Math.round(Math.random())));
                                }
                            }
                        } else if (this.fieldGrid[desX][desY] instanceof Wolf && this.fieldGrid[x][y] instanceof Sheep) {
                            ((Wolf) this.fieldGrid[desX][desY]).eatSheep((Sheep) this.fieldGrid[x][y]);
                        } else if (this.fieldGrid[desX][desY] instanceof Wolf && this.fieldGrid[x][y] instanceof Wolf) {
                            if (((Wolf) this.fieldGrid[desX][desY]).getGender() == ((Wolf) this.fieldGrid[x][y]).getGender()) {
                                ((Wolf) this.fieldGrid[x][y]).fight(((Wolf) this.fieldGrid[desX][desY]));
                            } else {
                                rdmCoordinate = this.getRandomEmpty();
                                if (rdmCoordinate[0] != -1) {
                                    //System.out.print(2);
                                    wolfBorn++;
                                    this.fieldGrid[rdmCoordinate[0]][rdmCoordinate[1]] = new Wolf(this.wolfHealth, (int) Math.round(Math.random()));
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int x = 0; x < this.sideLength; x++) {
            for (int y = 0; y < this.sideLength; y++) {
                if (this.fieldGrid[x][y] != null) {
                    if (this.fieldGrid[x][y].getHealth() < 1) {
                        if (this.fieldGrid[x][y] instanceof Sheep){
                            sheepDead++;
                        } else if (this.fieldGrid[x][y] instanceof Wolf){
                            wolfDead++;
                        }
                        this.fieldGrid[x][y] = null;
                    }
                }
            }
        }

        for (int i = 0; i < grassGrowRate; i++) {
            rdmCoordinate = this.getRandomEmpty();
            if (rdmCoordinate[0] != -1) {
                //System.out.print(3);
                this.fieldGrid[rdmCoordinate[0]][rdmCoordinate[1]] = new Grass(grassHealth);
            }
        }

        System.out.println("Sheep There = " + sheepThere + "; Wolf There = " + wolfThere + "; Sheep Born = " + sheepBorn + "; Wolf Born = " + wolfBorn + "; Sheep Eaten = " + sheepEaten + "; Sheep Dead = " + sheepDead + "; Wolf Dead = " + wolfDead);

    }

    public int selectMove(int coordinate) {
        double determinant = Math.random();

        if (determinant < 0.333) {
            if (coordinate == 0) {
                return 0;
            } else {
                return -1;
            }
        } else if (determinant > 0.333 && determinant < 0.667) {
            return 0;
        } else {
            if (coordinate == this.sideLength - 1) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public int[] getRandomEmpty() {
        ArrayList<int[]> coorList = new ArrayList<int[]>();
        int[] coordinates;

        for (int x = 0; x < this.sideLength; x++) {
            for (int y = 0; y < this.sideLength; y++) {
                if (this.fieldGrid[x][y] == null) {
                    coordinates = new int[2];
                    coordinates[0] = x;
                    coordinates[1] = y;
                    coorList.add(coordinates);
                }
            }
        }

        if (coorList.isEmpty()) {
            coordinates = new int[2];
            coordinates[0] = -1;
            coordinates[1] = -1;
        } else {
            int determinant = (int) (Math.random() * coorList.size());
            return coorList.get(determinant);
        }

        return coordinates;
    }

    public boolean isExtinct() {

        boolean sheepExtinct = true, wolfExtinct = true;

        for (Organism[] row : fieldGrid) {
            for (Organism org : row) {
                if (org instanceof Wolf) {
                    wolfExtinct = false;
                } else if (org instanceof Sheep) {
                    sheepExtinct = false;
                }
                if (!(sheepExtinct || wolfExtinct)) {
                    return false;
                }
            }
        }
        return true;
    }

}
