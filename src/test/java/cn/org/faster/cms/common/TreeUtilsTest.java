package cn.org.faster.cms.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TreeUtilsTest {

    static final class Node {
        private final Long id;
        private final Long parentId;
        private List<Node> children;

        Node(Long id, Long parentId) {
            this.id = id;
            this.parentId = parentId;
        }

        Long getId() {
            return id;
        }

        Long getParentId() {
            return parentId;
        }

        List<Node> getChildren() {
            return children;
        }

        void setChildren(List<Node> children) {
            this.children = children;
        }
    }

    @Test
    void buildsNestedTreeFromFlatList() {
        List<Node> flat = List.of(
                new Node(1L, 0L),
                new Node(2L, 1L),
                new Node(3L, 2L),
                new Node(4L, 0L));

        List<Node> roots = TreeUtils.build(flat, Node::getId, Node::getParentId, Node::setChildren);

        assertThat(roots).extracting(Node::getId).containsExactly(1L, 4L);
        assertThat(roots.get(0).getChildren()).extracting(Node::getId).containsExactly(2L);
        assertThat(roots.get(0).getChildren().get(0).getChildren())
                .extracting(Node::getId).containsExactly(3L);
        assertThat(roots.get(1).getChildren()).isNull();
    }

    @Test
    void treatsMissingParentAsRoot() {
        List<Node> flat = new ArrayList<>();
        flat.add(new Node(10L, 999L));

        List<Node> roots = TreeUtils.build(flat, Node::getId, Node::getParentId, Node::setChildren);

        assertThat(roots).extracting(Node::getId).containsExactly(10L);
    }

    @Test
    void ignoresSelfReferencingNode() {
        List<Node> flat = List.of(new Node(5L, 5L));

        List<Node> roots = TreeUtils.build(flat, Node::getId, Node::getParentId, Node::setChildren);

        assertThat(roots).extracting(Node::getId).containsExactly(5L);
    }
}
