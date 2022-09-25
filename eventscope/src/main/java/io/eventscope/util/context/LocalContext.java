package io.eventscope.util.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;

import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.function.Supplier;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RequestScoped
public class LocalContext {

    @Builder.Default
    Context context = Context.empty();

    public LocalContext() {
        this.context = Context.empty();
    }


    public String getUserId() {
        return context.getOrElse(Constant.USER_ID, () -> null);
    }

    public void setUserId(@NotBlank String userId) {
        this.set(Constant.USER_ID, userId);
    }

    public String getSessionId() {
        return context.getOrElse(Constant.SESSION_ID, () -> null);
    }

    public void setSessionId(@NotBlank String sessionId) {
        this.set(Constant.SESSION_ID, sessionId);
    }

    public String getTenantId() {
        return context.getOrElse(Constant.TENANT_ID, () -> null);
    }

    public void setTenantId(@NotBlank String tenantId) {
        this.set(Constant.TENANT_ID, tenantId);
    }


    public <T> T get(@NotBlank String key) {
        return this.getOrElse(key, () -> null);
    }

    public <T> T getOrElse(@NotBlank String key, @NotNull Supplier<? extends T> alternativeSupplier) {
        return context.getOrElse(key, alternativeSupplier);
    }

    public void set(@NotBlank String key, @NotNull Object value) {
        context.put(key, value);
    }

    public <T> void set(@NotBlank String key, T value, @NotNull T defaultValue) {
        if (!ObjectUtils.isEmpty(value))
            context.put(key, value);
        else
            context.put(key, defaultValue);
    }

    public void delete(@NotBlank String key) {
        context.delete(key);
    }
}