/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.function;

import javax.money.MonetaryAmount;



/**
 * This predicate allows filtering of {@link MonetaryAmount} instances using a
 * predicate and hereby counts the items that are matching. <br/>
 * Note that instances of this class are NOT thread-safe.
 * 
 * @author Anatole Tresch
 */
class MaxCountPredicate<T> implements MonetaryPredicate<T> {
	/** The minimal number of items that must match, or null. */
	private int max;
	/** The current number of items that matched the predicate. */
	private int currentNum;

	private MonetaryPredicate<? super T> predicate;

	/**
	 * Set the maximal number of items that are allowed to match the predicate
	 * passed to {@link #test(T)}. If less items match the whole
	 * {@link MaxCountPredicate} will not match.
	 * 
	 * @param max
	 *            The minimal number, or {@code null} to remove the condition.
	 * @return this, for chaining.
	 */
	MaxCountPredicate(int max,
			MonetaryPredicate<? super T> predicate) {
		this.max = max;
		this.predicate = predicate;
	}

	/**
	 * Method to check if the number of items is less than the minimum number
	 * configured.
	 * 
	 * @return true, if the current number counted is less than the minimum
	 *         number configured.
	 */
	protected boolean checkMaxFailed() {
		if (currentNum > max) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MaxCountPredicate [min=" + max + "]";
	}

	@Override
	public boolean test(T value) {
		if (predicate != null && predicate.test(value)) {
			currentNum++;
			if (checkMaxFailed()) {
				return false;
			}
		}
		return checkMaxFailed();
	}

}