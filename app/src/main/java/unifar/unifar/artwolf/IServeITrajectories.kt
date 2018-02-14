package unifar.unifar.artwolf

/**
 * Implement this to serve ITrajectory history.
 */
interface IServeITrajectories {
    fun onITrajectoriesHistoryIssued(trajectories: Collection<ITrajectory>)
}