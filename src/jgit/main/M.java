package jgit.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class M {

	public static final String path = "\\\\10.3.111.75\\git_files_java\\java-platform-v1\\app-3dgis.git";

	public static void main(String[] args) {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try {
			Repository repository = builder.setGitDir(new File(path)).readEnvironment().build();
			Git git = new Git(repository);
			Map<String, Ref> mapRef = repository.getTags();
			Set<Entry<String, Ref>> entryRef = mapRef.entrySet();
			for (Entry<String, Ref> item : entryRef) {
				String key = item.getKey();
				Ref value = item.getValue();
				ObjectId objectId = value.getObjectId();
//				ObjectLoader objectLoader = repository.open(objectId);
//				objectLoader.copyTo(System.out);
//				System.out.println(key + "--" + value.getName());
				
	            List<String> paths = readElementsAt(repository, objectId.getName(), "");
	            System.out.println("Had paths for commit: " + paths);
				
			}
			RevWalk walk = new RevWalk(repository);
			Iterator<RevCommit> it = walk.iterator();
			while (it.hasNext()) {
				RevCommit item = it.next();
				System.out.println(item.getShortMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private static List<String> readElementsAt(Repository repository, String commit, String path) throws IOException {
        RevCommit revCommit = buildRevCommit(repository, commit);

        // and using commit's tree find the path
        RevTree tree = revCommit.getTree();
        //System.out.println("Having tree: " + tree + " for commit " + commit);

        List<String> items = new ArrayList<>();

        // shortcut for root-path
        if(path.isEmpty()) {
            try (TreeWalk treeWalk = new TreeWalk(repository)) {
                treeWalk.addTree(tree);
                treeWalk.setRecursive(false);
                treeWalk.setPostOrderTraversal(false);

                while(treeWalk.next()) {
                    items.add(treeWalk.getPathString());
                }
            }
        } else {
            // now try to find a specific file
            try (TreeWalk treeWalk = buildTreeWalk(repository, tree, path)) {
                if((treeWalk.getFileMode(0).getBits() & FileMode.TYPE_TREE) == 0) {
                    throw new IllegalStateException("Tried to read the elements of a non-tree for commit '" + commit + "' and path '" + path + "', had filemode " + treeWalk.getFileMode(0).getBits());
                }

                try (TreeWalk dirWalk = new TreeWalk(repository)) {
                    dirWalk.addTree(treeWalk.getObjectId(0));
                    dirWalk.setRecursive(false);
                    while(dirWalk.next()) {
                        items.add(dirWalk.getPathString());
                    }
                }
            }
        }

        return items;
    }

    private static RevCommit buildRevCommit(Repository repository, String commit) throws IOException {
        // a RevWalk allows to walk over commits based on some filtering that is defined
        try (RevWalk revWalk = new RevWalk(repository)) {
           	revWalk.setTreeFilter(AndTreeFilter.create(PathFilter.create(path), TreeFilter.ANY_DIFF));
            return revWalk.parseCommit(ObjectId.fromString(commit));
        }
    }

    private static TreeWalk buildTreeWalk(Repository repository, RevTree tree, final String path) throws IOException {
        TreeWalk treeWalk = TreeWalk.forPath(repository, path, tree);
        if(treeWalk == null) {
            throw new FileNotFoundException("Did not find expected file '" + path + "' in tree '" + tree.getName() + "'");
        }
        return treeWalk;
    }
}
