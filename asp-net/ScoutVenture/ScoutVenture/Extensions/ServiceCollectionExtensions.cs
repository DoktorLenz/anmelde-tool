using Microsoft.AspNetCore.Authorization;

namespace ScoutVenture.Extensions
{
    public static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddKeycloakAuthentication(this IServiceCollection services, IConfiguration configuration)
        {
            services
                .AddAuthentication()
                .AddJwtBearer(options =>
                {
                    options.RequireHttpsMetadata = Convert.ToBoolean($"{configuration["Keycloak:require-https"]}");
                    options.MetadataAddress = $"{configuration["Keycloak:server-url"]}/realms/{configuration["Keycloak:realm"]}/.well-known/openid-configuration";
                    options.TokenValidationParameters = new Microsoft.IdentityModel.Tokens.TokenValidationParameters
                    {
                        RoleClaimType = "groups",
                        NameClaimType = "preferred_username",
                        ValidAudience = $"{configuration["Keycloak:client-id"]}",
                        ValidateIssuer = Convert.ToBoolean($"{configuration["Keycloak:validate-issuer"]}")
                    };
                });

            services.AddAuthorization(options =>
            {
                options.DefaultPolicy = new AuthorizationPolicyBuilder()
                .RequireAuthenticatedUser()
                .RequireClaim("email_verified", "true")
                .Build();
            });

            return services;
        }
    }
}
