package softprojlab.model.character;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import softprojlab.model.Game;
import softprojlab.model.field.Field;
import softprojlab.model.field.PlainField;
import softprojlab.model.item.agent.Agent;
import softprojlab.model.item.agent.BearAgent;
import softprojlab.model.item.agent.DanceAgent;
import softprojlab.model.item.equipment.*;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Nucleotide;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

public class VirologistValidator {
private static ArrayList<Aminoacid> aminoacidStorage;
private static ArrayList<Field> map;
private static Virologist v1, v2, v3;

@BeforeEach
void setUp() {
    map = new ArrayList<Field>();
    PlainField startLocation = new PlainField();
    PlainField loc2 = new PlainField();

    Virologist player = new Virologist(startLocation);
    map.add(startLocation);
    map.add(loc2);

    v1 = new Virologist(startLocation);
    v2 = new Virologist(startLocation);
    v3 = new Virologist(loc2 );

    DanceAgent da = new DanceAgent();
    BearAgent ba = new BearAgent();

    v1.setActionTokens(10000);
    for (int i=0; i<10000; i++) {
        v1.addAminoacid(new Aminoacid());
        v1.addNucleotide(new Nucleotide());
    }

}

@Test
void testAddAminoacid() {
    Virologist v = new Virologist(new PlainField() );
    Aminoacid aa = new Aminoacid();
    v.addAminoacid(aa);
    assertEquals(v.getAminoacidCount(), 1);
}

@Test
void testAddNukleotid() {
    Virologist v = new Virologist(new PlainField() );
    Nucleotide n = new Nucleotide();
    v.addNucleotide(n);
    assertEquals(v.getNucleotideCount(), 1);
}

@Test
void testSameField() {
    assertEquals(v1.getLocation(), v2.getLocation());
    assertNotEquals(v1.getLocation(), v3.getLocation());
}


@Test
void testApplyAgent() {
    DanceAgent da = new DanceAgent();

    LinkedHashSet<Agent> agents = v1.getLearnedAgents();
    v1.addCreatedAgent(da);
    v1.applyAgentTo(v2);
    ArrayList<Agent> checkedAgents = new ArrayList<Agent>();
    checkedAgents.add(da);
    assertEquals(v2.getAppliedAgents(), checkedAgents);

    BearAgent ba = new BearAgent();
    v1.addCreatedAgent(ba);
    v1.applyAgentTo(v2);
    checkedAgents.add(ba);
    assertEquals(v2.getAppliedAgents(), checkedAgents);

    assertNotEquals(v3.getAppliedAgents(), checkedAgents);
}


@Test
void testAddEquipment() {
    Bag bag=new Bag();
    assertTrue(v1.addEquipment(bag));
}
@Test
void testRemoveEquipment() {
    Bag bag=new Bag();
    v1.addEquipment(bag);
    assertEquals(v1.removeEquipment(bag ), bag);
}
@Test

void testRemoveAppliedAgent(){
    DanceAgent da = new DanceAgent();
    v1.addCreatedAgent(da);
    v1.applyAgentTo(v2);
    ArrayList<Agent> checkedAgents = new ArrayList<Agent>();
    checkedAgents.add(da);
    assertEquals(v2.getAppliedAgents(), checkedAgents);
    checkedAgents.remove(da);
    v2.removeAgent(da);
    assertEquals(v2.getAppliedAgents(), checkedAgents);
}

@Test
void testKill() {
    assertFalse(v1.kill(v2));
    Axe axe = new Axe();
    v1.addEquipment(axe);
    assertTrue(v1.kill(v2));
}



}