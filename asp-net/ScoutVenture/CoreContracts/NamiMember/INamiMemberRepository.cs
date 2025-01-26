namespace ScoutVenture.CoreContracts.NamiMember
{
    public interface INamiMemberRepository
    {
        void saveNamiMembers(List<NamiMember> namiMembers);
        List<NamiMember> getNamiMembers();
        NamiMember getNamiMember(long id);
        void updateNamiMember(NamiMember namiMember);
    }
}
