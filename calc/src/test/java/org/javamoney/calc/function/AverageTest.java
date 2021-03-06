/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.calc.function;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.javamoney.moneta.Money;
import org.junit.Test;

public class AverageTest {

	@Test
	public void testNew() {
		AverageMean avg = new AverageMean();
	}

	@Test
	public void testFromIterableOfMonetaryAmount() {
		Money m = Money.of(1, "CHF");
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of(1, "CHF"), MonetaryCalculations.average().calculate(set));
		set.add(m);
		set.add(m);
		assertEquals(Money.of(1, "CHF"), MonetaryCalculations.maximum().calculate(set));
		m = Money.of(3, "CHF");
		set.add(m);
		assertEquals(Money.of(1.5, "CHF"), MonetaryCalculations.average().calculate(set));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase1() {
		MonetaryCalculations.average().calculate((Iterable) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase2() {
		MonetaryCalculations.average().calculate(new HashSet());
	}

	@Test
	public void testApply() {
		Money m = Money.of(1, "CHF");
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of(1, "CHF"), MonetaryCalculations.average().calculate(set));
		set.add(m);
		set.add(m);
		m = Money.of(3, "CHF");
		set.add(m);
		assertEquals(Money.of(1.5, "CHF"), MonetaryCalculations.average().calculate(set));
	}

	@Test
	public void testToString() {
		assertEquals("Average Mean [Iterable<MonetaryAmount> -> MonetaryAmount]",
				new AverageMean().toString());
	}
}
