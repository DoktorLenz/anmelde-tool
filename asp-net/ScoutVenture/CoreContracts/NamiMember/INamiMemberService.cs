namespace ScoutVenture.CoreContracts.NamiMember
{
    public interface INamiMemberService
    {
        List<NamiMember> getNamiMembers();
        NamiMember getNamiMember(long id);
        void updateNamiMember(NamiMember namiMember);
        void importNamiMembers(List<NamiMember> namiMembers);
    }
}
