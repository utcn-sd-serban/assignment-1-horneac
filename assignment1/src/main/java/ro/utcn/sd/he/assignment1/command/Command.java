package ro.utcn.sd.he.assignment1.command;

import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

public interface Command {
    void execute(RepositoryFactory factory);
}
