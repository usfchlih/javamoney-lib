package org.javamoney.currencies.internal;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.spi.Bootstrap;

import org.javamoney.currencies.spi.CurrencyUnitMapperSpi;
import org.javamoney.currencies.spi.CurrencyUnitNamespaceProviderSpi;
import org.javamoney.currencies.spi.CurrencyMappingsSingletonSpi;

/**
 * Default implementation of {@link CurrencyMappingsSingletonSpi}, active
 * if no instance of {@link CurrencyMappingsSingletonSpi} was registered
 * using the {@link ServiceLoader}.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class DefaultMonetaryCurrenciesSingletonSpi implements
		CurrencyMappingsSingletonSpi {
	
	/**
	 * This method allows to evaluate, if the given currency namespace is
	 * defined. {@code "ISO-4217"} should be defined in all environments
	 * (default).
	 * 
	 * @param namespace
	 *            the required namespace
	 * @return {@code true}, if the namespace exists.
	 */
	@Override
	public boolean isNamespaceAvailable(String namespace) {
		for(CurrencyUnitNamespaceProviderSpi spi: Bootstrap.getServices(CurrencyUnitNamespaceProviderSpi.class)){
			if(spi.isNamespaceAvailable(namespace)){
				return true;
			}
		}
		return false;
	}

	/**
	 * This method allows to access all namespaces currently defined.
	 * "ISO-4217" should be defined in all environments (default).
	 * 
	 * @return the collection of currently defined namespace, never
	 *         {@code null}.
	 */
	@Override
	public Set<String> getNamespaces() {
		Set<String> ns = new HashSet<>();
		for(CurrencyUnitNamespaceProviderSpi spi: Bootstrap.getServices(CurrencyUnitNamespaceProviderSpi.class)){
			ns.addAll(spi.getNamespaces());
		}
		return ns;
	}

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	@Override
	public CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace) {
		for(CurrencyUnitMapperSpi spi: Bootstrap.getServices(CurrencyUnitMapperSpi.class)){
			CurrencyUnit mapped = spi.map(currencyUnit, targetNamespace, null);
			if(mapped!=null){
				return mapped;
			}
		}
		return null;
	}

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	@Override
	public CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace, long timestamp) {
		for(CurrencyUnitMapperSpi spi: Bootstrap.getServices(CurrencyUnitMapperSpi.class)){
			CurrencyUnit mapped = spi.map(currencyUnit, targetNamespace, timestamp);
			if(mapped!=null){
				return mapped;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getAll(java.lang
	 * .String)
	 */
	@Override
	public Set<CurrencyUnit> getCurrencies(String namespace) {
		Set<CurrencyUnit> result = new HashSet<>();
		for(CurrencyUnitNamespaceProviderSpi spi:Bootstrap.getServices(CurrencyUnitNamespaceProviderSpi.class)){
			result.addAll(spi.getCurrencies(namespace));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getNamespace(java
	 * .lang.String)
	 */
	@Override
	public Set<String> getNamespaces(String code) {
		Set<String> ns = new HashSet<>();
		for(CurrencyUnitNamespaceProviderSpi spi: Bootstrap.getServices(CurrencyUnitNamespaceProviderSpi.class)){
			ns.addAll(spi.getNamespaces(code));
		}
		return ns;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getNamespace(java
	 * .lang.String)
	 */
	@Override
	public Set<String> getNamespaces(CurrencyUnit code) {
		return getNamespaces(code.getCurrencyCode());
	}

}