namespace ScoutVenture.CoreContracts.NamiMember
{
    public class NamiMember
    {
        long MemberId { get; set; }
        string? FirstName { get; set; }
        string? LastName { get; set; }
        DateTimeOffset DateOfBirth { get; set; }

    }
}
