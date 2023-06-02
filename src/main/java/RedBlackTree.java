public class RedBlackTree<T extends Comparable<T>> {
    static class Node<T> {
        T value;
        boolean isRed;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        public Node(T value) {
            this.value = value;
            this.isRed = true;
        }
    }

    private Node<T> root;

    public void add(T value) {
        Node<T> node = new Node<>(value);

        if (root == null) {
            node.isRed = false; // корень всегда черный
            root = node;
        } else {
            Node<T> parent = null;
            Node<T> current = root;

            // поиск места для вставки
            while (current != null) {
                parent = current;

                if (value.compareTo(current.value) < 0) {
                    current = current.left;
                } else if (value.compareTo(current.value) > 0) {
                    current = current.right;
                } else {
                    // уже есть такой элемент в дереве
                    return;
                }
            }

            node.parent = parent;

            if (value.compareTo(parent.value) < 0) {
                parent.left = node;
            } else {
                parent.right = node;
            }

            // балансировка после добавления элемента
            balanceAfterInsert(node);
        }
    }

    private void balanceAfterInsert(Node<T> node) {
        while (node.parent != null && node.parent.isRed) {
            if (node.parent == node.parent.parent.left) {
                Node<T> uncle = node.parent.parent.right;

                if (uncle != null && uncle.isRed) {
                    // случай 1: перекрашиваем
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;

                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        // случай 2: левый поворот
                        node = node.parent;
                        rotateLeft(node);
                    }

                    // случай 3: правый поворот и перекрашивание
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;

                    rotateRight(node.parent.parent);
                }
            } else {
                Node<T> uncle = node.parent.parent.left;

                if (uncle != null && uncle.isRed) {
                    // случай 1: перекрашиваем
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;

                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        // случай 2: правый поворот
                        node = node.parent;
                        rotateRight(node);
                    }

                    // случай 3: левый поворот и перекрашивание
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;

                    rotateLeft(node.parent.parent);
                }
            }
        }

        root.isRed = false; // корень всегда черный
    }

    private void rotateLeft(Node<T> node) {
        Node<T> right = node.right;

        // правая ветвь становится левой ветвью
        node.right = right.left;
        if (right.left != null) {
            right.left.parent = node;
        }

        // заменяем node на right
        right.parent = node.parent;
        if (node.parent == null) {
            root = right;
        } else if (node == node.parent.left) {
            node.parent.left = right;
        } else {
            node.parent.right = right;
        }

        right.left = node;
        node.parent = right;
    }

    private void rotateRight(Node<T> node) {
        Node<T> left = node.left;

        // левая ветвь становится правой ветвью
        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }

        // заменяем node на left
        left.parent = node.parent;
        if (node.parent == null) {
            root = left;
        } else if (node == node.parent.right) {
            node.parent.right = left;
        } else {
            node.parent.left = left;
        }

        left.right = node;
        node.parent = left;
    }
}
