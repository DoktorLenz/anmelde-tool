using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Internal;

namespace ScoutVenture.PostgresAdapter
{
    public static class Seeding
    {
        private static readonly List<IdentityRole> Roles =
        [
            new() { Name = "Admin", NormalizedName = "ADMIN" },
            new() { Name = "User", NormalizedName = "USER" }
        ];
        public static void Seed(DbContext context, bool force = false)
        {
            var existingRoles = context.Set<IdentityRole>();
            var newRoles = Roles.Where(r => !existingRoles.Any((er => er.Name == r.Name)));
            context.Set<IdentityRole>().AddRange(newRoles);
            context.SaveChanges();
        }

        public static Task SeedAsync(DbContext context, bool force = false,
            CancellationToken cancellationToken = default)
        {
            var existingRoles = context.Set<IdentityRole>();
            var newRoles = Roles.Where(r => !existingRoles.Any((er => er.Name == r.Name)));
            context.Set<IdentityRole>().AddRangeAsync(newRoles, cancellationToken);
            return context.SaveChangesAsync(cancellationToken);
        }
    }
}