package io.openjob.server.cluster.util;

import io.openjob.common.context.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class ClusterUtilTest {
    @Test
    public void testGetKnowServersByTwo() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 3; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 2) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(1L);

        Assertions.assertEquals(knowServers, expect);
    }

    @Test
    public void testGetKnowServersByFour() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 5; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 4) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(1L);
        expect.add(2L);
        expect.add(3L);

        Assertions.assertEquals(knowServers, expect);
    }

    @Test
    public void testGetKnowServersByFive() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 6; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 3) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(1L);
        expect.add(2L);
        expect.add(4L);
        expect.add(5L);

        Assertions.assertEquals(knowServers, expect);
    }

    @Test
    public void testGetKnowServersBySix() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 7; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 3) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(1L);
        expect.add(2L);
        expect.add(4L);
        expect.add(5L);
        expect.add(6L);

        Assertions.assertEquals(knowServers, expect);
    }

    @Test
    public void testGetKnowServersBySeven() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 8; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 3) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(1L);
        expect.add(4L);
        expect.add(5L);
        expect.add(6L);
        expect.add(7L);

        Assertions.assertEquals(knowServers, expect);
    }

    @Test
    public void testGetKnowServersBySeven2() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 8; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 2) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(3L);
        expect.add(4L);
        expect.add(5L);
        expect.add(6L);
        expect.add(7L);

        Assertions.assertEquals(knowServers, expect);
    }

    @Test
    public void testGetKnowServersByEight() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 9; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 8) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(1L);
        expect.add(2L);
        expect.add(3L);
        expect.add(4L);
        expect.add(5L);
        Assertions.assertEquals(knowServers, expect);
    }

    @Test
    public void testGetKnowServersByEight2() {
        Map<Long, Node> nodesMap = new HashMap<>(16);
        Node currentNode = null;
        for (long i = 1; i < 9; i++) {
            Node node = new Node();
            node.setStatus(1);
            node.setServerId(i);
            node.setIp("127.0.0.1");
            node.setAkkaAddress(String.format("127.0.0.1:%d", i));
            if (i == 7) {
                currentNode = node;
            }

            nodesMap.put(i, node);
        }

        List<Long> knowServers = ClusterUtil.getKnowServers(nodesMap, currentNode, 5);
        List<Long> expect = new ArrayList<>();
        expect.add(1L);
        expect.add(2L);
        expect.add(3L);
        expect.add(4L);
        expect.add(8L);
        Assertions.assertEquals(knowServers, expect);
    }
}
