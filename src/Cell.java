class Cell {
    int x, y; // Coordinates of the cell
    String direction; // Direction of movement to reach this cell
    Cell parent; // Parent cell in the path

    // Constructor to initialize the cell
    public Cell(int x, int y, String direction, Cell parent) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.parent = parent;
    }
}

