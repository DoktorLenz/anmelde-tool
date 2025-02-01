using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace ScoutVenture.PostgresAdapter
{
    public class PostgresApplicationDbContext(DbContextOptions<PostgresApplicationDbContext> options)
        : IdentityDbContext<IdentityUser>(options)
    {
    }


}