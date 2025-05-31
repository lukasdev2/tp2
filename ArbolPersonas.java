package test;
import java.util.Comparator;


public class ArbolPersonas {
    public static void main(String[] args) {
        // Comparator por DNI (numérico)
        BinarySearchTree<Person> arbolDni = new BinarySearchTree<>(Comparator.comparingInt(Person::getDni));
        // Comparator por nombre (alfabético)
        BinarySearchTree<Person> arbolNombre = new BinarySearchTree<>(Comparator.comparing(Person::getNombre));

        // Insertar 15 personas de ejemplo
        Person[] lista = {
            new Person(33445566, "Ana"),
            new Person(11223344, "Luis"),
            new Person(55667788, "María"),
            new Person(22334455, "Pedro"),
            new Person(66778899, "Lucía"),
            new Person(44556677, "Carlos"),
            new Person(77889900, "Sofía"),
            new Person(88990011, "Diego"),
            new Person(99001122, "Elena"),
            new Person(10111213, "Jorge"),
            new Person(12131415, "Valeria"),
            new Person(14151617, "Martín"),
            new Person(16171819, "Laura"),
            new Person(18192021, "Andrés"),
            new Person(20212223, "Silvia")
        };
        for (Person p : lista) {
            arbolDni.insert(p);
            arbolNombre.insert(p);
        }

        // Mostrar recorridos
        System.out.println("=== Árbol ordenado por DNI ===");
        arbolDni.inOrder();
        System.out.println("--- Preorden ---"); arbolDni.preOrder();
        System.out.println("--- Postorden ---"); arbolDni.postOrder();

        System.out.println("=== Árbol ordenado por Nombre ===");
        arbolNombre.inOrder();
        System.out.println("--- Preorden ---"); arbolNombre.preOrder();
        System.out.println("--- Postorden ---"); arbolNombre.postOrder();

        // Búsquedas
        Person busq1 = new Person(22334455, null); // solo importa DNI
        System.out.println("¿Existe DNI 22334455? " + arbolDni.contains(busq1));
        Person busq2 = new Person(0, "Laura");    // solo importa nombre
        System.out.println("¿Existe Nombre 'Laura'? " + arbolNombre.contains(busq2));

        // Eliminaciones
        arbolDni.delete(new Person(55667788, null)); // elimina María
        arbolNombre.delete(new Person(0, "Andrés")); // elimina Andrés

        System.out.println("\n--- Árbol por DNI tras eliminar 55667788 ---");
        arbolDni.inOrder();
        System.out.println("--- Árbol por Nombre tras eliminar 'Andrés' ---");
        arbolNombre.inOrder();
    }
}

// Clase Persona con DNI y nombre
class Person {
    private int dni;
    private String nombre;

    public Person(int dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "[DNI=" + dni + ", Nombre='" + nombre + "']";
    }
}

// Árbol binario de búsqueda genérico
class BinarySearchTree<T> {
    private Node root;
    private Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    // Nodo interno
    private class Node {
        T data;
        Node left, right;
        Node(T data) { this.data = data; }
    }

    // Inserción pública
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node insert(Node node, T data) {
        if (node == null) return new Node(data);
        int cmp = comparator.compare(data, node.data);
        if (cmp < 0) node.left = insert(node.left, data);
        else if (cmp > 0) node.right = insert(node.right, data);
        // si cmp == 0, no inserta duplicados
        return node;
    }

    // Búsqueda
    public boolean contains(T data) {
        return contains(root, data);
    }

    private boolean contains(Node node, T data) {
        if (node == null) return false;
        int cmp = comparator.compare(data, node.data);
        if (cmp == 0) return true;
        return cmp < 0 ? contains(node.left, data) : contains(node.right, data);
    }

    // Eliminación pública
    public void delete(T data) {
        root = delete(root, data);
    }

    private Node delete(Node node, T data) {
        if (node == null) return null;
        int cmp = comparator.compare(data, node.data);
        if (cmp < 0) {
            node.left = delete(node.left, data);
        } else if (cmp > 0) {
            node.right = delete(node.right, data);
        } else {
            // caso 1 o 2 hijos
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // caso 3: dos hijos, reemplazo por sucesor mínimo
            Node min = findMin(node.right);
            node.data = min.data;
            node.right = delete(node.right, min.data);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Recorridos
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }
    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }
    private void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void postOrder() {
        postOrder(root);
        System.out.println();
    }
    private void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.data + " ");
        }
    }
}
