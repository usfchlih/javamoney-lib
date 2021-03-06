package org.javamoney.cdi.impl;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.spi.MonetaryAmountsSpi;

@Singleton
public class CDIAmountProvider implements MonetaryAmountsSpi {

	@Inject
	private Instance<MonetaryAmountFactory<?>> factories;

	// cast is safe (self managed)
	@SuppressWarnings("unchecked")
	@Override
	public <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(
			Class<T> amountType) {
		for (MonetaryAmountFactory<?> f : factories) {
			if (f.getAmountType().isAssignableFrom(amountType)) {
				return (MonetaryAmountFactory<T>) f;
			}
		}
		return null;
	}

	@Override
	public Set<Class<? extends MonetaryAmount>> getAmountTypes() {
		Set<Class<? extends MonetaryAmount>> types = new HashSet<>();
		for (MonetaryAmountFactory<?> f : factories) {
			types.add(f.getAmountType());
		}
		return types;
	}

	@Override
	public Class<? extends MonetaryAmount> getDefaultAmountType() {
		// TODO check system property
		for (MonetaryAmountFactory<?> f : factories) {
			return f.getAmountType();
		}
		return null;
	}

	@Override
	public Class<? extends MonetaryAmount> queryAmountType(
			MonetaryContext requiredContext) {
		// TODO Auto-generated method stub
		return getDefaultAmountType();
	}

}
