package org.eclipse.hawkbit.repository;

import org.eclipse.hawkbit.im.authentication.SpPermission.SpringEvalExpressions;
import org.eclipse.hawkbit.repository.model.TenantConfiguration;
import org.eclipse.hawkbit.repository.model.TenantConfigurationValue;
import org.eclipse.hawkbit.tenancy.configuration.TenantConfigurationKey;
import org.eclipse.hawkbit.tenancy.configuration.validator.TenantConfigurationValidatorException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TenantConfigurationManagement {

    /**
     * Retrieves a configuration value from the e.g. tenant overwritten
     * configuration values or in case the tenant does not a have a specific
     * configuration the global default value hold in the {@link Environment}.
     * 
     * @param <T>
     *            the type of the configuration value
     * @param configurationKey
     *            the key of the configuration
     * @param propertyType
     *            the type of the configuration value, e.g. {@code String.class}
     *            , {@code Integer.class}, etc
     * @return the converted configuration value either from the tenant specific
     *         configuration stored or from the fallback default values or
     *         {@code null} in case key has not been configured and not default
     *         value exists
     * @throws TenantConfigurationValidatorException
     *             if the {@code propertyType} and the value in general does not
     *             match the expected type and format defined by the Key
     * @throws ConversionFailedException
     *             if the property cannot be converted to the given
     *             {@code propertyType}
     */
    @PreAuthorize(value = SpringEvalExpressions.HAS_AUTH_TENANT_CONFIGURATION + SpringEvalExpressions.HAS_AUTH_OR
            + SpringEvalExpressions.IS_SYSTEM_CODE)
    <T> TenantConfigurationValue<T> getConfigurationValue(TenantConfigurationKey configurationKey,
            Class<T> propertyType);

    /**
     * Build the tenant configuration by the given key
     * 
     * @param configurationKey
     *            the key
     * @param propertyType
     *            the property type
     * @param tenantConfiguration
     *            the configuration
     * @return <null> if no default value is set and no database value available
     *         or returns the tenant configuration value
     */
    @PreAuthorize(value = SpringEvalExpressions.HAS_AUTH_TENANT_CONFIGURATION + SpringEvalExpressions.HAS_AUTH_OR
            + SpringEvalExpressions.IS_SYSTEM_CODE)
    <T> TenantConfigurationValue<T> buildTenantConfigurationValueByKey(TenantConfigurationKey configurationKey,
            Class<T> propertyType, TenantConfiguration tenantConfiguration);

    /**
     * Retrieves a configuration value from the e.g. tenant overwritten
     * configuration values or in case the tenant does not a have a specific
     * configuration the global default value hold in the {@link Environment}.
     * 
     * @param configurationKey
     *            the key of the configuration
     * @return the converted configuration value either from the tenant specific
     *         configuration stored or from the fall back default values or
     *         {@code null} in case key has not been configured and not default
     *         value exists
     * @throws TenantConfigurationValidatorException
     *             if the {@code propertyType} and the value in general does not
     *             match the expected type and format defined by the Key
     * @throws ConversionFailedException
     *             if the property cannot be converted to the given
     *             {@code propertyType}
     */
    @PreAuthorize(value = SpringEvalExpressions.HAS_AUTH_TENANT_CONFIGURATION + SpringEvalExpressions.HAS_AUTH_OR
            + SpringEvalExpressions.IS_SYSTEM_CODE)
    TenantConfigurationValue<?> getConfigurationValue(TenantConfigurationKey configurationKey);

    /**
     * returns the global configuration property either defined in the property
     * file or an default value otherwise.
     * 
     * @param <T>
     *            the type of the configuration value
     * @param configurationKey
     *            the key of the configuration
     * @param propertyType
     *            the type of the configuration value, e.g. {@code String.class}
     *            , {@code Integer.class}, etc
     * @return the global configured value
     * @throws TenantConfigurationValidatorException
     *             if the {@code propertyType} and the value in the property
     *             file or the default value does not match the expected type
     *             and format defined by the Key
     * @throws ConversionFailedException
     *             if the property cannot be converted to the given
     *             {@code propertyType}
     */
    @PreAuthorize(value = SpringEvalExpressions.HAS_AUTH_TENANT_CONFIGURATION + SpringEvalExpressions.HAS_AUTH_OR
            + SpringEvalExpressions.IS_SYSTEM_CODE)
    <T> T getGlobalConfigurationValue(TenantConfigurationKey configurationKey, Class<T> propertyType);

    /**
     * Adds or updates a specific configuration for a specific tenant.
     * 
     * 
     * @param configurationKey
     *            the key of the configuration
     * @param value
     *            the configuration value which will be written into the
     *            database.
     * @return the configuration value which was just written into the database.
     * @throws TenantConfigurationValidatorException
     *             if the {@code propertyType} and the value in general does not
     *             match the expected type and format defined by the Key
     * @throws ConversionFailedException
     *             if the property cannot be converted to the given
     */
    @PreAuthorize(value = SpringEvalExpressions.HAS_AUTH_TENANT_CONFIGURATION)
    <T> TenantConfigurationValue<T> addOrUpdateConfiguration(TenantConfigurationKey configurationKey, T value);

    /**
     * Deletes a specific configuration for the current tenant. Does nothing in
     * case there is no tenant specific configuration value.
     *
     * @param configurationKey
     *            the configuration key to be deleted
     */
    @PreAuthorize(value = SpringEvalExpressions.HAS_AUTH_TENANT_CONFIGURATION)
    void deleteConfiguration(TenantConfigurationKey configurationKey);

}