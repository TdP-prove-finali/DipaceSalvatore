package it.polito.tdp.gestione_magazzino_lego.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;

public class TestUtil {

	@Test
	public void testArraysCompare1() {
		List<Integer> firstList = new ArrayList<Integer>();
		firstList.add(1);
		firstList.add(5);
		firstList.add(10);

		List<Integer> secondList = new ArrayList<Integer>();
		secondList.add(1);
		secondList.add(5);
		secondList.add(10);

		assertTrue(firstList.equals(secondList));

	}

	@Test
	public void testArraysCompare2() {
		List<Integer> firstList = new ArrayList<Integer>();
		firstList.add(1);
		firstList.add(5);
		firstList.add(10);

		List<Integer> secondList = new ArrayList<Integer>();
		secondList.add(10);
		secondList.add(1);
		secondList.add(5);

		assertFalse(firstList.equals(secondList));

	}

	
	@Test
	public void testArraysCompare3() {
		List<Integer> firstList = new ArrayList<Integer>();
		firstList.add(1);
		firstList.add(5);
		firstList.add(10);

		List<Integer> secondList = new ArrayList<Integer>();
		secondList.add(10);
		secondList.add(1);
		secondList.add(5);

		Collections.sort(firstList);
		Collections.sort(secondList);
		
		assertTrue(firstList.equals(secondList));

	}

	@Test
	public void testArraysCompare4() {
		Set setA = new Set("A", "set A", Year.now(), 110, "salva");
		Set setB = new Set("B", "set B", Year.now(), 120, "salva");
		Set setC = new Set("C", "set C", Year.now(), 200, "salva");
		Set setD = new Set("D", "set D", Year.now(), 80, "salva");
	
		
		List<Set> firstList = new ArrayList<Set>();
		firstList.add(setA);
		firstList.add(setD);
		firstList.add(setC);
		firstList.add(setB);

		List<Set> secondList = new ArrayList<Set>();
		secondList.add(setC);
		secondList.add(setD);
		secondList.add(setB);
		secondList.add(setA);

		Collections.sort(firstList);
		Collections.sort(secondList);
		
		assertTrue(firstList.equals(secondList));

	}

}
